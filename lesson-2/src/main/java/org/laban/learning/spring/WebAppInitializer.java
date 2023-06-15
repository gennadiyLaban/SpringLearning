package org.laban.learning.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import lombok.SneakyThrows;
import org.h2.server.web.WebServlet;
import org.laban.learning.spring.app.config.AppContextConfig;
import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.config.WebContextConfig;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Order(2)
public class WebAppInitializer implements WebApplicationInitializer {

    @SneakyThrows
    @Override
    public void onStartup(@NonNull ServletContext servletContext) throws ServletException {
        Logger logger = LogFactory.getLogger(WebAppInitializer.class);
        try {
            logger.info("loading app config");
            AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
            appContext.register(AppContextConfig.class);
            servletContext.addListener(new ContextLoaderListener(appContext));

            logger.info("loading web config");
            AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
            webContext.register(WebContextConfig.class);

            DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

            ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                    "my-dispatcher-servlet",
                    dispatcherServlet
            );
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");
            logger.info("dispatcher ready");

            ServletRegistration.Dynamic h2Servlet = servletContext.addServlet(
                    "h2-console",
                    new WebServlet()
                );
            h2Servlet.setLoadOnStartup(2);
            h2Servlet.addMapping("/console/*");

        } catch (Throwable th) {
            logger.error("Start error", th);
            throw th;
        }
    }
}
