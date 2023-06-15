package org.laban.learning.spring.app.services.books;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.laban.learning.spring.app.exceptions.DomainException;
import org.laban.learning.spring.app.services.ProjectRepository;
import org.laban.learning.spring.app.services.Qualifiers;
import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.dto.Book;
import org.laban.learning.spring.web.dto.BookRegexToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class BooksService {
    private final Logger logger = LogFactory.getLogger(BooksService.class);
    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BooksService(@Qualifier(value= Qualifiers.BOOKS) ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public boolean saveBook(Book book) {
        if (isEmpty(book)) {
            return false;
        }

        bookRepo.store(book);
        return true;
    }

    private boolean isEmpty(Book book) {
        return isNullOrEmpty(book.getAuthor())
                && isNullOrEmpty(book.getTitle())
                && (book.getSize() == null || book.getSize() < 0);
    }

    private boolean isNullOrEmpty(@Nullable String value) {
        return null == value || value.isEmpty();
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByRegex(@NonNull String target, @NonNull String regex) {
        var regexPattern = Pattern.compile(regex);
        var predicate = createPredicateFor(target, regexPattern);

        var deletedBooks = bookRepo.retrieveAll()
                .stream()
                .filter(predicate)
                .peek(book -> bookRepo.removeItemById(book.getId()))
                .map(String::valueOf)
                .toList();

        if (!deletedBooks.isEmpty()) {
            logger.info("Books was deleted: %s, current repository size is %d".formatted(
                    String.join("; ", deletedBooks),
                    bookRepo.retrieveAll().size()
            ));
            return true;
        } else {
            logger.info("Noting books was deleted");
            return false;
        }
    }

    @NonNull
    private Predicate<Book> createPredicateFor(@NonNull String target, @NonNull Pattern pattern) {
        Predicate<String> strPredicate = pattern.asMatchPredicate();
        return switch (target) {
            case BookRegexToRemove.TARGET_AUTHOR -> book -> strPredicate.test(orEmpty(book.getAuthor()));
            case BookRegexToRemove.TARGET_TITLE -> book -> strPredicate.test(orEmpty(book.getTitle()));
            case BookRegexToRemove.TARGET_SIZE -> book -> strPredicate.test(orEmpty(book.getSize()));
            default -> {
                logger.info("Target is unknown: %s".formatted(target));
                throw new DomainException("Unknown target type");
            }
        };
    }

    @NonNull
    private String orEmpty(@Nullable String value) {
        return value == null ? "" : value;
    }

    @NonNull
    private String orEmpty(@Nullable Integer value) {
        return value == null ? "" : String.valueOf(value);
    }
}
