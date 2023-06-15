package org.laban.learning.spring.web.controllers;

import org.laban.learning.spring.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/404")
    public String notFoundError(Model model) {
        model.addAttribute("rootPath", Environment.getInstance().DEPLOY_PATH);
        return "errors/404";
    }

}
