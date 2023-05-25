package org.laban.learning.spring.app.services;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.dto.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private Logger logger = LogFactory.getLogger(LoginService.class);

    public boolean authenticate(LoginForm loginForm) {
        logger.info("try auth with user-form: %s".formatted(loginForm));
        return loginForm.getUsername().equals("root") && loginForm.getPassword().equals("root");
    }
}
