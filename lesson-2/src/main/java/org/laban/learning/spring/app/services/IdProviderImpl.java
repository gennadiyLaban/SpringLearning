package org.laban.learning.spring.app.services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IdProviderImpl implements IdProvider,
        InitializingBean,
        DisposableBean,
        ApplicationContextAware {
    private final Logger logger = LogFactory.getLogger(IdProviderImpl.class);


    @NonNull
    @Override
    public String provideId(@NonNull Book book) {
        return "%d_%d".formatted(this.hashCode(), book.hashCode());
    }

    @Override
    public void setApplicationContext(
            @NonNull ApplicationContext applicationContext
    ) throws BeansException {
        logger.info("ApplicationContextAware.setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("InitializingBean.afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("DisposableBean.destroy");
    }

    @PostConstruct
    public void postConstructIdProvider() {
        logger.info("postConstructIdProvider");
    }

    @PreDestroy
    public void preDestroyIdProvider() {
        logger.info("preDestroyIdProvider");
    }
}
