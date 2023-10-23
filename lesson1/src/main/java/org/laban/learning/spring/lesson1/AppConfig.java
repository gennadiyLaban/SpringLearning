package org.laban.learning.spring.lesson1;

import java.util.Objects;

import org.laban.learning.spring.lesson1.config.Profiles;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan("org.laban.learning.spring.lesson1")
public class AppConfig {
    private static final String PROFILE_PROPERTY = "spring.profiles.active";
    private static final String DEFAULT_APPLICATION_PROPERTIES = "application.yaml";
    private static final String PROFILE_APPLICATION_PROPERTIES_TEMPLATE = "application-%s.yaml";

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        var defaultClasspathResource = new ClassPathResource(DEFAULT_APPLICATION_PROPERTIES);
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(defaultClasspathResource);

        var profile = Objects.requireNonNull(yamlPropertiesFactoryBean.getObject())
                .getProperty(PROFILE_PROPERTY, Profiles.DEFAULT);
        if (!Profiles.DEFAULT.equals(profile)) {
            yamlPropertiesFactoryBean.setResources(
                    defaultClasspathResource,
                    new ClassPathResource(PROFILE_APPLICATION_PROPERTIES_TEMPLATE.formatted(profile))
            );
            yamlPropertiesFactoryBean.afterPropertiesSet();
        }

        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setProperties(Objects.requireNonNull(yamlPropertiesFactoryBean.getObject()));
        return configurer;
    }
}
