package org.laban.learning.spring.web.validation;

import javax.validation.GroupSequence;

import org.laban.learning.spring.web.validation.bookregextoremove.BookRegexToRemoveGroup;
import org.laban.learning.spring.web.validation.groups.NotEmptyGroup;

@GroupSequence(value = {
        NotEmptyGroup.class,
        BookRegexToRemoveGroup.TargetPart.class,
        BookRegexToRemoveGroup.RegexPart.class
})
public @interface OrderChecks {}
