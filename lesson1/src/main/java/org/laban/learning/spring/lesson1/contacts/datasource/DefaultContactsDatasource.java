package org.laban.learning.spring.lesson1.contacts.datasource;

import org.laban.learning.spring.lesson1.config.Profiles;
import org.springframework.stereotype.Component;

@Profiles.Default
@Component
public class DefaultContactsDatasource extends BaseContactsDatasource {
}
