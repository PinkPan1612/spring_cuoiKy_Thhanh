package com.example.demo.controller.client;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @GetMapping("/access-deny")
    public String gethomePage() {
        return "denyPage";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

}
