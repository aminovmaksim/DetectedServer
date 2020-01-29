package com.devian.detected.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController()
@SuppressWarnings("unused")
public class LinkController {

    @RequestMapping(value = "/link")
    private RedirectView redirectToPlayMarket() {
        return new RedirectView("https://www.google.com");
    }
}
