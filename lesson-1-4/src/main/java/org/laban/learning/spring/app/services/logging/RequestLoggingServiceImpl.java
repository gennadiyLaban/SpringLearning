package org.laban.learning.spring.app.services.logging;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.function.Function;

@Service
public class RequestLoggingServiceImpl implements RequestLoggingService {
    private Logger logger = Logger.getLogger(RequestLoggingService.class);

    @Override
    public void logRequest(final HttpServletRequest request, Object body) {
        logger.info(
                """
                INCOMING REQUEST
                Request: %s %s
                body: %s""".formatted(
                        request.getMethod(), request.getServletPath(),
                        String.valueOf(body)
                )
        );
    }

    private String buildAttributes(final HttpServletRequest request) {
        final Iterator<String> iterator = (Iterator<String>) request.getAttributeNames().asIterator();
        Iterable<String> iterable = () -> wrapIterator(
                iterator,
                (String attributeName) -> String.valueOf(request.getAttribute(attributeName))
        );
        return String.join("; ", iterable);


    }

    private <T, R> Iterator<R> wrapIterator(final Iterator<T> source, final Function<T, R> wrapAction) {
        return new Iterator<R>() {
            @Override
            public boolean hasNext() {
                return source.hasNext();
            }

            @Override
            public R next() {
                return wrapAction.apply(source.next());
            }
        };
    }

    @Override
    public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        logger.info(
                """
                OUT COMING RESPONSE
                Response body: %s
                For request: %s %s
                """.formatted(
                        String.valueOf(body),
                        request.getMethod(), request.getServletPath()
                )
        );
    }
}
