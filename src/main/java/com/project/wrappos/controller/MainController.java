package com.project.wrappos.controller;

import com.project.wrappos.security.ShopUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {

        if (authentication != null
                && authentication.getPrincipal() instanceof ShopUserDetails) {
            return "redirect:/main";
        }

        return "login";
    }

    @GetMapping("/main")
    public String dashboard(@AuthenticationPrincipal ShopUserDetails userDetails, Model model) {

//        model.addAttribute("bizName", userDetails.getBizName());
        model.addAttribute("pageTitle", "Dashboard v3");

        return "pages/dashboard";
    }
}