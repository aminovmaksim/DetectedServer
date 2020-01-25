package com.devian.detected.controllers;

import com.devian.detected.domain.network.Response;
import com.devian.detected.utils.NetworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping(value = "/testConnection", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> getProfile() {
        log.info("Test connection request");

        return NetworkService.getInstance().proceedResponse(Response.TYPE_DEFAULT);
    }

}
