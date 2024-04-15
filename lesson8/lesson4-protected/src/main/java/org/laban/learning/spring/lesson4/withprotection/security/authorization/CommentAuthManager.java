package org.laban.learning.spring.lesson4.withprotection.security.authorization;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.model.RoleType;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.service.CommentService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.comment.CommentRequestDTO;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentAuthManager {
    private final CommentService commentService;

    public boolean hasPermissionForUpdate(AppUserDetails user, CommentRequestDTO commentRequestDTO) {
        var hasUserRole = user.getRoles().contains(RoleType.ROLE_USER);
        var hasModeratorRole = user.getRoles().contains(RoleType.ROLE_MODERATOR);
        var hasAdminRole = user.getRoles().contains(RoleType.ROLE_ADMIN);

        return (hasUserRole || hasModeratorRole || hasAdminRole)
                && isCommentOwner(user.getId(), commentRequestDTO.getId());
    }

    public boolean hasPermissionsForDelete(AppUserDetails user, Long commentId) {
        var hasUserRole = user.getRoles().contains(RoleType.ROLE_USER);
        var hasModeratorRole = user.getRoles().contains(RoleType.ROLE_MODERATOR);
        var hasAdminRole = user.getRoles().contains(RoleType.ROLE_ADMIN);
        var hasAnyRole = hasUserRole || hasModeratorRole || hasAdminRole;

        return (hasAnyRole && isCommentOwner(user.getId(), commentId))
                || hasModeratorRole
                || hasAdminRole;
    }

    private boolean isCommentOwner(@Nonnull Long userId, @Nonnull Long commentId) {
        return commentService.isCommentOwner(userId, commentId);
    }
}
