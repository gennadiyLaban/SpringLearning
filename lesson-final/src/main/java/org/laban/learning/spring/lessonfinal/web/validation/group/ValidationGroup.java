package org.laban.learning.spring.lessonfinal.web.validation.group;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;
import org.laban.learning.spring.lessonfinal.web.validation.custom.booking.BookingValidationGroup;

public interface ValidationGroup {
    interface Update extends Default {
    }

    interface Create extends Default {

    }

    @GroupSequence({Create.class, BookingValidationGroup.class})
    interface BookingCreate {

    }

    interface NestedObject {

    }
}
