package org.laban.learning.spring.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.laban.learning.spring.web.validation.bookregextoremove.BookRegexToRemoveGroup;
import org.laban.learning.spring.web.validation.bookregextoremove.TargetRegexPart;
import org.laban.learning.spring.web.validation.bookregextoremove.ValuableRegexPart;
import org.laban.learning.spring.web.validation.groups.NotEmptyGroup;

import javax.validation.constraints.NotEmpty;

@Data @NoArgsConstructor
public class BookRegexToRemove {
    public static final char DELIMITER = ':';

    public static final String TARGET_AUTHOR = "author";
    public static final String TARGET_TITLE = "title";
    public static final String TARGET_SIZE = "size";

    @NotEmpty(
            message = "Input field must not be empty",
            groups = NotEmptyGroup.class
    )
    @TargetRegexPart(
            acceptedValues = { TARGET_AUTHOR, TARGET_TITLE, TARGET_SIZE },
            message = "Unknown target part",
            groups = BookRegexToRemoveGroup.TargetPart.class
    )
    @ValuableRegexPart(
            message = "Invalid regex part",
            groups = BookRegexToRemoveGroup.RegexPart.class
    )
    private String regex;

    public String targetPart() {
        return regex.substring(0, regex.indexOf(DELIMITER));
    }

    public String regexPart() {
        return regex.substring(regex.indexOf(DELIMITER) + 1);
    }
}
