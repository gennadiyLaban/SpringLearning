package org.laban.learning.spring.web.validation;

import org.laban.learning.spring.web.validation.bookregextoremove.BookRegexToRemoveGroup;
import org.laban.learning.spring.web.validation.groups.NotEmptyGroup;

import javax.validation.GroupSequence;

@GroupSequence(value = {
        NotEmptyGroup.class,
        BookRegexToRemoveGroup.TargetPart.class,
        BookRegexToRemoveGroup.RegexPart.class
})
public @interface OrderChecks {}
