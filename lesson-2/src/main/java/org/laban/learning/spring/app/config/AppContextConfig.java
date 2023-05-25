package org.laban.learning.spring.app.config;

import org.laban.learning.spring.app.services.processor.CustomBeanPostProcessor;
import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan("org.laban.learning.spring.app")
@Import({ EnvironmentConfig.class })
public class AppContextConfig {
    private final Logger logger = LogFactory.getLogger(AppContextConfig.class);

    @Bean("customBeanPostProcessor")
    public CustomBeanPostProcessor customBeanPostProcessor() {
        return new CustomBeanPostProcessor();
    }

    @Bean("myBeansValidator")
    public LocalValidatorFactoryBean myBeansValidator() {
        return new LocalValidatorFactoryBean();
    }
}
