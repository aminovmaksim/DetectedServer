package com.devian.detected.controllers;

import com.devian.detected.domain.Response;
import com.devian.detected.domain.User;
import com.devian.detected.repository.UserRepository;
import com.devian.detected.security.AES256;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController()
public class AuthController {

    private Gson gson = new Gson();

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/auth")
    private ResponseEntity<Response> auth(
            @RequestHeader(value = "data") String data
    ) {
        //decrypt incoming data
        String userData = AES256.decrypt(data);
        log.info("New Auth: " + userData);

        User user = gson.fromJson(userData, User.class);
        user.setLastLogin(new Date());

        userRepository.save(user);

        return new ResponseEntity<>(new Response(10), HttpStatus.OK);
    }

}
