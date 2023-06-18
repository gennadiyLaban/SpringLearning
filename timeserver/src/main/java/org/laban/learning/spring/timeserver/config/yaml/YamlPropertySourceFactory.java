package org.laban.learning.spring.timeserver.config.yaml;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

public class YamlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties loadedProperties = loadYamlIntoProperties(resource.getResource());

        return new PropertiesPropertySource(isNotBlank(name) ? name : resource.getResource().getFilename(), loadedProperties);
    }

    private boolean isNotBlank(@Nullable String value) {
        return null != value
                && !value.isEmpty()
                && value.chars().anyMatch(codePoint -> !Character.isWhitespace(codePoint));
    }

    private Properties loadYamlIntoProperties(Resource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }
}
