package com.devian.detected.controllers;

import com.devian.detected.domain.User;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {

    private Gson gson = new Gson();

    @GetMapping(value = "/getProfile", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getProfile() {
        return new ResponseEntity<>(
                gson.toJson(new User(UUID.randomUUID(), "zortan", "aminovmaksim@gmail.com")),
                HttpStatus.OK);
    }

}
