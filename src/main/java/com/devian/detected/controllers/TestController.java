package com.devian.detected.controllers;

import com.devian.detected.domain.network.Response;
import com.devian.detected.utils.NetworkManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class TestController {

    @GetMapping(value = "/test_connection", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> getProfile() {
        log.info("Test connection request");

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_DEFAULT);
    }

}
