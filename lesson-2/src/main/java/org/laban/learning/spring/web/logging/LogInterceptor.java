package org.laban.learning.spring.web.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.laban.learning.spring.app.services.logging.RequestLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    RequestLoggingService loggingService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {

        if (request.getMethod().equals(HttpMethod.GET.name())) {
            loggingService.logRequest(request, null);
        }


        return true;
    }
}
