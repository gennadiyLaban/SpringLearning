package org.laban.learning.spring.lesson4.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.Nullable;
import org.laban.learning.spring.lesson4.exception.AccessDeniedException;
import org.laban.learning.spring.lesson4.exception.InternalServerError;
import org.laban.learning.spring.lesson4.model.Comment;
import org.laban.learning.spring.lesson4.model.Post;
import org.laban.learning.spring.lesson4.web.dto.comment.CommentRequestDTO;
import org.laban.learning.spring.lesson4.web.dto.post.PostRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class CheckAuthorizationAspect {
    private static final String USER_ID_HEADER = "User_id";

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

        Long requestedUserId = extractRequestedUserId(joinPoint, checkAuthorization);
        if (!authUserId.equals(requestedUserId)) {
            throw new AccessDeniedException("Wrong authorization header");
        }
    }

    @Nullable
    private Long extractRequestedUserId(JoinPoint joinPoint, CheckAuthorization checkAuthorization) {
        var checkedParamName = checkAuthorization.paramName();
        if (checkedParamName.isBlank()) {
            throw new InternalServerError("Unexpected error during authorization");
        }

        var signature = (MethodSignature) joinPoint.getSignature();
        var paramNames = signature.getParameterNames();
        var argIndex = indexOf(paramNames, checkedParamName);
        var argType = signature.getParameterTypes()[argIndex];

        Long requestedUserId = null;
        var arg = Objects.requireNonNull(joinPoint.getArgs()[argIndex]);
        if (argType.equals(Long.class)) {
            requestedUserId = (Long) arg;
        } else if (argType.equals(PostRequestDTO.class)) {
            requestedUserId = ((PostRequestDTO) arg).getUserId();
        } else if (argType.equals(Post.class)) {
            requestedUserId = ((Post) arg).getUser().getId();
        } else if (argType.equals(CommentRequestDTO.class)) {
            requestedUserId = ((CommentRequestDTO) arg).getUserId();
        } else if (argType.equals(Comment.class)) {
            requestedUserId = ((Comment) arg).getUser().getId();
        }
        return requestedUserId;
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
