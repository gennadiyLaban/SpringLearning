package org.laban.learning.spring.lesson4.withprotection.security.authorization;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson4.withprotection.model.RoleType;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.service.PostService;
import org.laban.learning.spring.lesson4.withprotection.web.dto.post.PostRequestDTO;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostAuthManager {
    private final PostService postService;

    public boolean hasPermissionForUpdate(AppUserDetails user, PostRequestDTO postRequestDTO) {
        var hasUserRole = user.getRoles().contains(RoleType.ROLE_USER);
        var hasModeratorRole = user.getRoles().contains(RoleType.ROLE_MODERATOR);
        var hasAdminRole = user.getRoles().contains(RoleType.ROLE_ADMIN);

        return (hasUserRole || hasModeratorRole || hasAdminRole)
                && isPostOwner(user.getId(), postRequestDTO.getId());
    }

    public boolean hasPermissionsForDelete(AppUserDetails user, Long postId) {
        var hasUserRole = user.getRoles().contains(RoleType.ROLE_USER);
        var hasModeratorRole = user.getRoles().contains(RoleType.ROLE_MODERATOR);
        var hasAdminRole = user.getRoles().contains(RoleType.ROLE_ADMIN);
        var hasAnyRole = hasUserRole || hasModeratorRole || hasAdminRole;

        return (hasAnyRole && isPostOwner(user.getId(), postId))
                || hasModeratorRole
                || hasAdminRole;
    }

    private boolean isPostOwner(@Nonnull Long userId, @Nonnull Long postId) {
        return postService.isPostOwner(userId, postId);
    }
}
