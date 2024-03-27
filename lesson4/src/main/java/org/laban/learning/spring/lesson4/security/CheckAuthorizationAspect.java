package org.laban.learning.spring.lesson4.security;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.Nullable;
import org.laban.learning.spring.lesson4.exception.AccessDeniedException;
import org.laban.learning.spring.lesson4.exception.InternalServerError;
import org.laban.learning.spring.lesson4.model.Comment;
import org.laban.learning.spring.lesson4.model.Post;
import org.laban.learning.spring.lesson4.service.CommentService;
import org.laban.learning.spring.lesson4.service.PostService;
import org.laban.learning.spring.lesson4.web.dto.comment.CommentRequestDTO;
import org.laban.learning.spring.lesson4.web.dto.post.PostRequestDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Aspect
@Component
public class CheckAuthorizationAspect {
    private static final String USER_ID_HEADER = "User_id";

    private final PostService postService;
    private final CommentService commentService;

    @Before("@annotation(checkAuthorization)")
    public void checkPostAuthority(JoinPoint joinPoint, CheckAuthorization checkAuthorization) {
        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        var userIdHeader = requestAttributes.getRequest().getHeader(USER_ID_HEADER);
        Long authUserId;
        try {
            authUserId = Long.parseLong(userIdHeader);
        } catch (NumberFormatException exception) {
            throw new AccessDeniedException("Missing authorization header");
        }

        Long targetEntityId = extractTargetEntityId(joinPoint, checkAuthorization);
        Long entityUserId = extractUserIdByTargetEntity(checkAuthorization.checkedEntity(), targetEntityId);
        if (!authUserId.equals(entityUserId)) {
            throw new AccessDeniedException("Wrong authorization header");
        }
    }

    @Nonnull
    private Long extractTargetEntityId(JoinPoint joinPoint, CheckAuthorization checkAuthorization) {
        var checkedParamName = checkAuthorization.paramName();
        var checkedEntityType = checkAuthorization.checkedEntity();
        if (checkedParamName.isBlank()) {
            throw new InternalServerError("Unexpected error during authorization");
        }

        var signature = (MethodSignature) joinPoint.getSignature();
        var paramNames = signature.getParameterNames();
        var argIndex = indexOf(paramNames, checkedParamName);
        var argType = signature.getParameterTypes()[argIndex];
        var argValue = Objects.requireNonNull(joinPoint.getArgs()[argIndex]);

        Long requestedUserId = null;
        if (checkedEntityType.equals(Post.class)) {
            requestedUserId = extractPostId(argType, argValue);
        } else if (checkedEntityType.equals(Comment.class)) {
            requestedUserId = extractCommentId(argType, argValue);
        }

        if (requestedUserId == null) {
            throw new InternalServerError("Unexpected error during authorization");
        }
        return requestedUserId;
    }

    @Nullable
    private Long extractPostId(Class<?> argType, Object argValue) {
        if (argType.equals(Long.class)) {
            return (Long) argValue;
        } else if (argType.equals(PostRequestDTO.class)) {
            return ((PostRequestDTO) argValue).getId();
        } else if (argType.equals(Post.class)) {
            return ((Post) argValue).getId();
        }
        return null;
    }

    @Nullable
    private Long extractCommentId(Class<?> argType, Object argValue) {
        if (argType.equals(Long.class)) {
            return (Long) argValue;
        } else if (argType.equals(CommentRequestDTO.class)) {
            return ((CommentRequestDTO) argValue).getId();
        } else if (argType.equals(Comment.class)) {
            return ((Comment) argValue).getId();
        }
        return null;
    }

    @Nonnull
    private Long extractUserIdByTargetEntity(@Nonnull Class<?> checkedEntityType, @Nonnull Long entityId) {
        Long entityUserId = null;
        if (checkedEntityType.equals(Post.class)) {
            entityUserId = postService.getPostById(entityId).getUser().getId();
        } else if (checkedEntityType.equals(Comment.class)) {
            entityUserId = commentService.getCommentById(entityId).getUser().getId();
        }

        if (entityUserId == null) {
            throw new InternalServerError("Unexpected error during authorization");
        }
        return entityUserId;
    }

    private int indexOf(String[] paramNames, String target) {
        var index = -1;
        for (int i = 0; i < paramNames.length; i++) {
            if (target.equals(paramNames[i])) {
                index = i;
                break;
            }
        }
        return index;
    }
}
