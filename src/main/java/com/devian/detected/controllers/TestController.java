package com.devian.detected.controllers;

import com.devian.detected.domain.Response;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    private Gson gson = new Gson();

    @GetMapping(value = "/testConnection", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getProfile() {

        log.info("test connection request");

        return new ResponseEntity<>(
                gson.toJson(new Response(Response.TYPE_DEFAULT, "Connection established")),
                HttpStatus.OK);
    }

}
