package org.laban.learning.spring.web.controllers;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.Environment;
import org.laban.learning.spring.app.exceptions.login.BookShelfLoginException;
import org.laban.learning.spring.app.services.LoginService;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private Logger logger = LogFactory.getLogger(LoginController.class);
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String home(Model model) {
        logger.info("Get /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping(value = "/auth")
    public String authenticate(LoginForm loginForm) throws BookShelfLoginException {
        if (loginService.authenticate(loginForm)) {
            logger.info("login OK, redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("login Fail, redirect to login");
            throw new BookShelfLoginException("Invalid username or password");
        }
    }

    @ExceptionHandler(BookShelfLoginException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String handleError(Model model, Exception e) {
        logger.info("Handle BookShelfLoginException: ", e);
        model.addAttribute("errorMessage", e.getMessage());
        return "errors/auth_exception";
    }
}
