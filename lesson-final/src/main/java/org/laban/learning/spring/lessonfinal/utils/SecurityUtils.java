package org.laban.learning.spring.lessonfinal.utils;

import org.laban.learning.spring.lessonfinal.security.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private SecurityUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Long getCurrentUserId() {
        return getCurrentUserDetails().getUserId();
    }

    public static AppUserDetails getCurrentUserDetails() {
        return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
