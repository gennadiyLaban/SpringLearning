package org.laban.learning.spring.lesson4.withprotection.security.authorization;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.laban.learning.spring.lesson4.withprotection.exception.InternalServerError;
import org.laban.learning.spring.lesson4.withprotection.security.AppUserDetails;
import org.laban.learning.spring.lesson4.withprotection.web.dto.user.UserDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Aspect
@Component
public class CheckAuthorizationAspect {
    private final UserAuthManager userAuthManager;

    @Before("@annotation(userGet)")
    public void checkUserGetAuthorization(JoinPoint joinPoint, CheckAuthorization.UserGet userGet) {
        var principal = getPrincipal();
        var targetUserId = extractTargetUserId(joinPoint, userGet.paramName());

        handleDecision(userAuthManager.hasPermissionForGetById(principal, targetUserId));
    }

    @Before("@annotation(userList)")
    public void checkUserGetAuthorization(JoinPoint joinPoint, CheckAuthorization.UserList userList) {
        var principal = getPrincipal();

        handleDecision(userAuthManager.hasPermissionForGetUserList(principal));
    }

    @Before("@annotation(userDelete)")
    public void checkUserGetAuthorization(JoinPoint joinPoint, CheckAuthorization.UserDelete userDelete) {
        var principal = getPrincipal();
        var targetUserId = extractTargetUserId(joinPoint, userDelete.paramName());

        handleDecision(userAuthManager.hasPermissionForDelete(principal, targetUserId));
    }

    @Before("@annotation(userUpdate)")
    public void checkUserGetAuthorization(JoinPoint joinPoint, CheckAuthorization.UserUpdate userUpdate) {
        var principal = getPrincipal();
        var targetParam = extractTargetParam(joinPoint, userUpdate.paramName());

        if (!targetParam.argType.equals(UserDTO.class)) {
            throw new InternalServerError("Unexpected error during authorization");
        }

        handleDecision(userAuthManager.hasPermissionForUpdate(principal, (UserDTO) targetParam.argValue));
    }

    private void handleDecision(boolean granted) {
        if (!granted) {
            throw new AccessDeniedException("Access Denied");
        }
    }

    private AppUserDetails getPrincipal() {
        return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Nonnull
    private Long extractTargetUserId(JoinPoint joinPoint, String paramName) {
        var param = extractTargetParam(joinPoint, paramName);
        Long requestedUserId = null;
        if (param.argType.equals(Long.class)) {
            requestedUserId = (Long) param.argValue;
        } else if (param.argType.equals(UserDTO.class)) {
            requestedUserId = ((UserDTO) param.argValue).getId();
        }

        if (requestedUserId == null) {
            throw new InternalServerError("Unexpected error during authorization");
        }
        return requestedUserId;
    }

    private Param extractTargetParam(JoinPoint joinPoint, String paramName) {
        if (paramName.isBlank()) {
            throw new InternalServerError("Unexpected error during authorization");
        }

        var signature = (MethodSignature) joinPoint.getSignature();
        var paramNames = signature.getParameterNames();
        var argIndex = indexOf(paramNames, paramName);
        var argType = signature.getParameterTypes()[argIndex];
        var argValue = Objects.requireNonNull(joinPoint.getArgs()[argIndex]);

        return new Param(argType, argValue);
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

    private record Param(Class<?> argType, Object argValue) {

    }
}
