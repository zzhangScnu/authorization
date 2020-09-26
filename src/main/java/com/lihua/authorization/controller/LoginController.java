package com.lihua.authorization.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @RequestMapping("/login_p")
    public String login() {
        return "login_p";
    }

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        setAttribute(model);
        return "index";
    }

    private void setAttribute(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("authorities", authentication.getAuthorities().toString());
        }
    }

    @RequestMapping("/user")
    public String user(Model model) {
        setAttribute(model);
        return "user/user";
    }

    @RequestMapping("/depart1")
    public String depart1(Model model) {
        setAttribute(model);
        return "depart1/depart1";
    }

    @RequestMapping("/depart2")
    public String depart2(Model model) {
        setAttribute(model);
        return "depart2/depart2";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        setAttribute(model);
        return "admin/admin";
    }
}
