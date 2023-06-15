package org.laban.learning.spring.web.config;

import org.laban.learning.spring.app.config.EnvironmentConfig;
import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "org.laban.learning.spring.web" })
@Import({ EnvironmentConfig.class })
public class WebContextConfig implements WebMvcConfigurer {
    private final Logger logger = LogFactory.getLogger(WebContextConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/images");
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);

        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);

        return viewResolver;
    }

    @Bean("multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        var multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1024 * 1024 * 5); // 5mb
        return multipartResolver;
    }
}
