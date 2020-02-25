package com.devian.detected.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/policy")
    public String getPolitics() {
        return "policy.html";
    }
}
