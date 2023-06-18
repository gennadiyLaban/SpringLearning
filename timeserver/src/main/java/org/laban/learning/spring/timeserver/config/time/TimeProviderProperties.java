package org.laban.learning.spring.timeserver.config.time;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tprov")
public class TimeProviderProperties {
    private String profile;
    private String description;
    private String format;

}
