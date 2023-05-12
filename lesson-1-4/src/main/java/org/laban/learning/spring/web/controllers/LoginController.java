package org.laban.learning.spring.web.controllers;

import org.apache.log4j.Logger;
import org.laban.learning.spring.app.services.LoginService;
import org.laban.learning.spring.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);
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
    public String authenticate(LoginForm loginForm) {
        if (loginService.authenticate(loginForm)) {
            logger.info("login OK, redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("login Fail, redirect to login");
            return "redirect:/login";
        }
    }
}
