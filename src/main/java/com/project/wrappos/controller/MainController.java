package com.project.wrappos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping({"/main"})
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard v3");
        return "pages/dashboard";
    }
}
