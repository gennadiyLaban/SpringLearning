package org.laban.learning.spring.app.services;

import org.apache.log4j.Logger;
import org.laban.learning.spring.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
public class BookService {
    private final Logger logger = Logger.getLogger(BookService.class);
    private final ProjectRepostitory<Book> bookRepo;

    @Autowired
    public BookService(@Qualifier(value=Qualifiers.BOOKS) ProjectRepostitory<Book> bookRepo) {
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

    public boolean removeBookById(long bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByRegex(@Nullable String regexRequest) {
        if (isNullOrEmpty(regexRequest)) return false;

        var delimiterIndex = regexRequest.indexOf(':');
        if (delimiterIndex == -1 || delimiterIndex == regexRequest.length() - 1) return false;

        var target = regexRequest.substring(0, delimiterIndex);
        var regex = regexRequest.substring(delimiterIndex + 1);
        var regexPattern = buildRegex(regex);
        if (null == regexPattern) return false;

        var predicate = createPredicateFor(target, regexPattern);
        if(predicate == null) return false;

        var deletedBooks = bookRepo.retrieveAll()
                .stream()
                .filter(predicate)
                .peek((book) -> bookRepo.removeItemById(book.getId()))
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

    @Nullable
    private Pattern buildRegex(String regex) {
        Pattern pattern = null;
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            logger.info("Compile regex pattern error", e);
        }
        return pattern;
    }

    private Predicate<Book> createPredicateFor(String target, Pattern pattern) {
        Predicate<String> strPredicate = pattern.asMatchPredicate();
        return switch (target) {
            case "author" -> (book) -> strPredicate.test(orEmpty(book.getAuthor()));
            case "title" -> (book) -> strPredicate.test(orEmpty(book.getTitle()));
            case "size" -> (book) -> strPredicate.test(orEmpty(book.getSize()));
            default -> {
                logger.info("Target is unknown: %s".formatted(target));
                yield null;
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
