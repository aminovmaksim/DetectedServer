package com.devian.detected.controllers;

import com.devian.detected.domain.Response;
import com.devian.detected.domain.User;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import com.devian.detected.repository.UserRepository;
import com.devian.detected.security.AES256;
import com.devian.detected.utils.TimeManager;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController()
public class AuthController {

    private Gson gson = new Gson();

    private final Database database;

    public AuthController(Database database) {
        this.database = database;
    }

    @GetMapping(value = "/auth")
    private ResponseEntity<Response> auth(
            @RequestHeader(value = "data") String data
    ) {
        //decrypt incoming data
        String userData = AES256.decrypt(data);
        log.info("New Auth: " + userData);

        User user = gson.fromJson(userData, User.class);
        user.setLastLogin(TimeManager.getCurrentTime());

        Optional<UserStats> optionalUserStats = database.getStatsRepository().findByUid(user.getUid());
        if (!optionalUserStats.isPresent()) {
            UserStats userStats = new UserStats(user.getUid(), 0, 0);
            database.getStatsRepository().save(userStats);
        }

        database.getUserRepository().save(user);

        return new ResponseEntity<>(new Response(Response.TYPE_AUTH_SUCCESS), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserInfo")
    private ResponseEntity<Response> getUserInfo(
            @RequestHeader(value = "data") String data
    ) {
        //decrypt incoming data
        String uid = AES256.decrypt(data);
        log.info("New getUserInfo request: " + uid);

        Optional<User> optionalUser = database.getUserRepository().findByUid(uid);
        if (optionalUser.isPresent()) {
            String response = gson.toJson(optionalUser.get());
            return new ResponseEntity<>(new Response(Response.TYPE_AUTH_SUCCESS, response), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(Response.TYPE_AUTH_FAILED), HttpStatus.OK);
        }
    }
}
