package org.laban.learning.spring.lesson4.withprotection.web.validation.group;

import jakarta.validation.groups.Default;

public interface ValidationGroup {
    interface Update extends Default {
    }

    interface Create extends Default {

    }
}
