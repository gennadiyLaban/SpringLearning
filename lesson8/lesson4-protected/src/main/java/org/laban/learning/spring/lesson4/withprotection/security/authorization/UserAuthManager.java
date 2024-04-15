package org.laban.learning.spring.lesson4.withprotection.security.authorization;

import org.laban.learning.spring.lesson4.withprotection.model.RoleType;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.web.dto.user.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserAuthManager {
    public boolean hasPermissionForGetById(AppUserDetails user, Long userId) {
        var hasUserRole = user.getRoles().contains(RoleType.ROLE_USER);
        var hasModeratorRole = user.getRoles().contains(RoleType.ROLE_MODERATOR);
        var hasAdminRole = user.getRoles().contains(RoleType.ROLE_ADMIN);
        var selfUpdate = user.getId().equals(userId);

        return hasModeratorRole || hasAdminRole
                || (hasUserRole && selfUpdate);
    }

    public boolean hasPermissionForGetUserList(AppUserDetails user) {
        return user.getRoles().contains(RoleType.ROLE_ADMIN);
    }

    public boolean hasPermissionForUpdate(AppUserDetails user, UserDTO value) {
        var hasUserRole = user.getRoles().contains(RoleType.ROLE_USER);
        var hasModeratorRole = user.getRoles().contains(RoleType.ROLE_MODERATOR);
        var hasAdminRole = user.getRoles().contains(RoleType.ROLE_ADMIN);
        var selfUpdate = user.getId().equals(value.getId());

        if (!hasUserRole && !hasModeratorRole && !hasAdminRole) {
            return false; // only user with roles USER, MODERATOR, ADMIN can update User.class info
        }

        if (!selfUpdate && (Objects.nonNull(value.getUsername()) || Objects.nonNull(value.getPassword()))) {
            return false; // only user-owner can update self username and password
        }

        return hasAdminRole || !Objects.nonNull(value.getRoles()); // only user-admin can update user roles
    }

    public boolean hasPermissionForDelete(AppUserDetails user, Long userId) {
        var hasUserRole = user.getRoles().contains(RoleType.ROLE_USER);
        var hasModeratorRole = user.getRoles().contains(RoleType.ROLE_MODERATOR);
        var hasAdminRole = user.getRoles().contains(RoleType.ROLE_ADMIN);
        var selfUpdate = user.getId().equals(userId);

        return hasModeratorRole || hasAdminRole
                || (hasUserRole && selfUpdate);
    }
}
