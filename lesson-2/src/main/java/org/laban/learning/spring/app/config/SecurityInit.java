package org.laban.learning.spring.app.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(1)
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
