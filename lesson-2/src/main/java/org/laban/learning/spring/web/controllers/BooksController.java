package org.laban.learning.spring.web.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import lombok.Builder;
import org.laban.learning.spring.Environment;
import org.laban.learning.spring.app.exceptions.books.NoBookToRemoveException;
import org.laban.learning.spring.app.exceptions.books.RemoveBookByRegexValidationException;
import org.laban.learning.spring.app.exceptions.books.UploadBookFileValidationException;
import org.laban.learning.spring.app.services.books.BooksService;
import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.dto.Book;
import org.laban.learning.spring.web.dto.BookIdToRemove;
import org.laban.learning.spring.web.dto.BookRegexToRemove;
import org.laban.learning.spring.web.dto.UploadBookFile;
import org.laban.learning.spring.web.validation.OrderChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BooksController {
    private static final String PAGE_VIEW_BOOK_SHELF = "book_shelf";
    private static final String PAGE_VIEW_REDIRECT_BOOK_SHELF = "redirect:/books/shelf";

    private static final String MODEL_KEY_BOOK = "book";
    private static final String MODEL_KEY_BOOK_LIST = "bookList";
    private static final String MODEL_KEY_BOOK_ID_TO_REMOVE = "bookIdToRemove";
    private static final String MODEL_KEY_BOOK_REGEX_TO_REMOVE = "bookRegexToRemove";
    private static final String MODEL_KEY_UPLOAD_BOOK_FILE = "uploadBookFile";

    private static final String ERROR_MESSAGE_KEY_BOOK_REGEX_TO_REMOVE = "bookRegexToRemoveErrorMessage";
    private static final String ERROR_MESSAGE_KEY_UPLOAD_BOOK_FILE = "uploadBookFileErrorMessage";

    private static final String UPLOAD_FILES_DIR_NAME = "external_upload";
    private static final String UPLOAD_FILES_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";


    private final Logger logger = LogFactory.getLogger(BooksController.class);
    @Autowired
    private BooksService bookService;
    @Autowired
    private Environment environment;

    @GetMapping("/shelf")
    public String booksShelf(Model model) {
        logger.info("GET books/shelf returns book_shelf.html");
        return bookShelfBuilder(model).build().view();
    }

    @PostMapping("/save")
    public String saveBook(
            @Valid @ModelAttribute(MODEL_KEY_BOOK) Book book,
            BindingResult bindingResult,
            Model model
    ) {
        logger.info("POST books/save with %s".formatted(book));
        if (bindingResult.hasErrors()) {
            return bookShelfBuilder(model)
                    .addNewBook(book)
                    .build().view();
        } else if (!bookService.saveBook(book)) {
            bindingResult.addError(new ObjectError(MODEL_KEY_BOOK, "Saving failed"));
            logger.info("Saving book {} failed", book);
            return bookShelfBuilder(model)
                    .addNewBook(book)
                    .build().view();
        }

        logger.info("Saving successfull current repository size: {}}", bookService.getAllBooks().size());
        return PAGE_VIEW_REDIRECT_BOOK_SHELF;
    }

    @PostMapping("/remove")
    public String remove(
            @Valid @ModelAttribute(MODEL_KEY_BOOK_ID_TO_REMOVE) BookIdToRemove bookIdToRemove,
            BindingResult bindingResult,
            Model model
    ) {
        logger.info("POST books/remove with %s".formatted(bookIdToRemove.getId()));
        logger.info("POST books/remove bindingResult.hasErrors(): %b".formatted(bindingResult.hasErrors()));
        if (bindingResult.hasErrors()) {
            return bookShelfBuilder(model)
                    .bookIdToRemove(bookIdToRemove)
                    .build().view();
        } else if (!bookService.removeBookById(bookIdToRemove.getId())) {
            bindingResult.addError(new ObjectError(MODEL_KEY_BOOK_ID_TO_REMOVE, "Book to remove not found"));
            return bookShelfBuilder(model)
                    .bookIdToRemove(bookIdToRemove)
                    .build().view();
        }

        return PAGE_VIEW_REDIRECT_BOOK_SHELF;
    }

    @PostMapping("/removeByRegex")
    public String removeByRegex(
            @Validated(value = { OrderChecks.class })
            @ModelAttribute(MODEL_KEY_BOOK_REGEX_TO_REMOVE)
            BookRegexToRemove dto,
            BindingResult bindingResult,
            Model model
    ) throws Exception {
        logger.info("POST books/removeByRegex with %s".formatted(dto.getRegex()));

        if (bindingResult.hasErrors()) {
            logger.info("Failed validation for %s".formatted(dto));
            throw new RemoveBookByRegexValidationException(bindingResult, model, dto);
        }

        if (!bookService.removeBookByRegex(dto.targetPart(), dto.regexPart())) {
            logger.info("Failed removing with %s".formatted(dto.getRegex()));
            throw new NoBookToRemoveException(model, dto, bindingResult);
        }

        logger.info("Successful removing with %s".formatted(dto.getRegex()));
        return PAGE_VIEW_REDIRECT_BOOK_SHELF;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(
            @Valid
            @ModelAttribute(MODEL_KEY_UPLOAD_BOOK_FILE)
            UploadBookFile dto,
            BindingResult bindingResult,
            Model model
        ) throws IOException {
        logger.info("Post books/uploadFile");
        if (bindingResult.hasErrors()) {
            logger.info("Failed validation for uploading file");
            throw new UploadBookFileValidationException(bindingResult, model, dto);
        }

        var file = dto.getFile();
        logger.info("Post books/uploadFile, filename: \"{}\", contentType: {}, size: {}",
                file.getOriginalFilename(), file.getContentType(), file.getSize());

        String uploadingFileName = file.getOriginalFilename();
        logger.info("POST /uploadFile with file %s".formatted(uploadingFileName));

        // create dir
        var uploadDirPath = Path.of(environment.HOME_DIRECTORY, UPLOAD_FILES_DIR_NAME);
        var uploadDirFile = uploadDirPath.toFile();
        if (!uploadDirFile.exists()) {
            Files.createDirectory(uploadDirPath);
        }

        // create server file
        var serverFilePath = uploadDirPath.resolve(Objects.requireNonNull(uploadingFileName));
        if (serverFilePath.toFile().exists()) {
            var originFileName = serverFilePath.getFileName().toString();
            logger.info("file %s is already exists".formatted(originFileName));
            var extIndex = originFileName.lastIndexOf('.');
            var fileNameWithoutExt = extIndex == -1 ? originFileName : originFileName.substring(0, extIndex);
            var extWithDot = extIndex == -1 ? "" : originFileName.substring(extIndex);

            var dateFormat = new SimpleDateFormat(UPLOAD_FILES_DATE_FORMAT);
            var newFileName = "%s%s%s".formatted(
                    fileNameWithoutExt, dateFormat.format(new Date()), extWithDot
            );
            serverFilePath = serverFilePath.resolveSibling(newFileName);
            logger.info("will be created file %s".formatted(serverFilePath.getFileName().toString()));
        }

        try (var in = file.getInputStream()) {
            try (var out = Files.newOutputStream(
                    serverFilePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                var buffer = new byte[1024];
                var len = in.read(buffer);
                while (len != -1) {
                    out.write(buffer, 0, len);
                    len = in.read(buffer);
                }
            }
        }

        logger.info("file %s was uploaded".formatted(serverFilePath.getFileName().toString()));

        return PAGE_VIEW_REDIRECT_BOOK_SHELF;
    }

    @ExceptionHandler(value = RemoveBookByRegexValidationException.class)
    public String handleRemoveBookByRegexValidationException(
            Model model,
            RemoveBookByRegexValidationException exception
    ) {
        logger.info("handle RemoveBookByRegexValidationException");
        var binding = exception.getBindingResult();
        var oldModel = exception.getOldModel();

        var errMessage = Objects.requireNonNull(binding.getFieldError()).getDefaultMessage();
        return bookShelfBuilder(model)
                .oldModel(oldModel)
                .skipBookRegexToRemove(true)
                .other(Collections.singletonMap(ERROR_MESSAGE_KEY_BOOK_REGEX_TO_REMOVE, errMessage))
                .build().view();
    }

    @ExceptionHandler(value = NoBookToRemoveException.class)
    public String handleNoBookToRemoveException(
            Model model,
            NoBookToRemoveException exception
    ) {
        logger.info("handle NoBookToRemoveException");
        var binding = exception.getBindingResult();
        var oldModel = exception.getOldModel();
        var dto = exception.getDto();

        var errorMsg = "Failed to remove by %s \"regex\"".formatted(dto.getRegex());
        binding.addError(new ObjectError(ERROR_MESSAGE_KEY_BOOK_REGEX_TO_REMOVE, errorMsg));
        return bookShelfBuilder(model)
                .oldModel(oldModel)
                .skipBookIdToRemove(true)
                .other(Collections.singletonMap(ERROR_MESSAGE_KEY_BOOK_REGEX_TO_REMOVE, errorMsg))
                .build().view();
    }

    @ExceptionHandler(value = UploadBookFileValidationException.class)
    public String handleUploadBookFileValidationError(
            Model model,
            UploadBookFileValidationException exception
    ) {
        logger.info("handle UploadBookFileValidationException");
        var binding = exception.getBindingResult();
        var oldModel = exception.getOldModel();

        var errorMsg = Objects.requireNonNull(binding.getFieldError()).getDefaultMessage();
        return bookShelfBuilder(model)
                .oldModel(oldModel)
                .skipUploadBookFile(true)
                .other(Collections.singletonMap(ERROR_MESSAGE_KEY_UPLOAD_BOOK_FILE, errorMsg))
                .build().view();
    }

    private BookShelfView.BookShelfViewBuilder bookShelfBuilder(@NonNull Model model) {
        return BookShelfView.builder().model(model).booksService(bookService);
    }

    @Builder
    private static class BookShelfView {
        @lombok.NonNull private BooksService booksService;
        @lombok.NonNull private Model model;
        @Nullable private Model oldModel;
        @Nullable private Book addNewBook;
        private boolean skipAddNewBook;
        @Nullable private List<Book> bookList;
        private boolean skipBookList;
        @Nullable private BookIdToRemove bookIdToRemove;
        private boolean skipBookIdToRemove;
        @Nullable private BookRegexToRemove bookRegexToRemove;
        private boolean skipBookRegexToRemove;
        @Nullable private UploadBookFile uploadBookFile;
        private boolean skipUploadBookFile;
        @Nullable private Map<String, Object> other;

        public String view() {
            if (model == null || booksService == null) {
                throw new IllegalStateException("model and books service must be initialized");
            }

            Map<String, Object> oldParams = null != oldModel ? oldModel.asMap() : Collections.emptyMap();
            addNewBook = null != addNewBook || skipAddNewBook ? addNewBook : new Book();
            bookList =  null != bookList || skipBookList ? bookList : booksService.getAllBooks();
            bookIdToRemove = null != bookIdToRemove || skipBookIdToRemove ? bookIdToRemove : new BookIdToRemove();
            bookRegexToRemove = null != bookRegexToRemove || skipBookRegexToRemove ? bookRegexToRemove : new BookRegexToRemove();
            uploadBookFile = null != uploadBookFile || skipUploadBookFile ? uploadBookFile : new UploadBookFile();
            other = null != other ? other : Collections.emptyMap();

            model.addAllAttributes(oldParams);
            if (!skipAddNewBook) model.addAttribute(MODEL_KEY_BOOK, addNewBook);
            if (!skipBookList) model.addAttribute(MODEL_KEY_BOOK_LIST, bookList);
            if (!skipBookIdToRemove) model.addAttribute(MODEL_KEY_BOOK_ID_TO_REMOVE, bookIdToRemove);
            if (!skipBookRegexToRemove) model.addAttribute(MODEL_KEY_BOOK_REGEX_TO_REMOVE, bookRegexToRemove);
            if (!skipUploadBookFile) model.addAttribute(MODEL_KEY_UPLOAD_BOOK_FILE, uploadBookFile);
            model.addAllAttributes(other);

            return PAGE_VIEW_BOOK_SHELF;
        }
    }
}
