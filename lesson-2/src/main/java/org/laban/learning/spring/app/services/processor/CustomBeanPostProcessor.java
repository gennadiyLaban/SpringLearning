package org.laban.learning.spring.app.services.processor;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomBeanPostProcessor implements BeanPostProcessor {
    private final Logger logger = LogFactory.getLogger(CustomBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        logger.info("postProcessBeforeInitialization: %s: %s".formatted(beanName, bean));
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        logger.info("postProcessAfterInitialization: %s: %s".formatted(beanName, bean));
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
