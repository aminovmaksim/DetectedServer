package com.devian.detected.controllers;

import com.devian.detected.domain.Response;
import com.devian.detected.domain.User;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import com.devian.detected.security.AES256;
import com.devian.detected.utils.GsonSerializer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController()
public class AuthController {

    private Gson gson = GsonSerializer.getInstance().getGson();

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

        Optional<User> optionalUser = database.getUserRepository().findByUid(user.getUid());
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        user.setLastLogin(new Date());
        database.getUserRepository().save(user);

        Optional<UserStats> optionalUserStats = database.getStatsRepository().findByUid(user.getUid());
        if (!optionalUserStats.isPresent()) {
            UserStats userStats = new UserStats(user.getUid(), 0, 0);
            database.getStatsRepository().save(userStats);
        }

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

    @PostMapping(value = "/changeNickname")
    private ResponseEntity<Response> changeNickname(
            @RequestHeader(value = "data") String data
    ) {
        //decrypt incoming data
        String userData = AES256.decrypt(data);
        log.info("New changeNickname request: " + userData);

        User user = gson.fromJson(userData, User.class);

        Optional<User> optionalUser = database.getUserRepository().findByUid(user.getUid());
        if (optionalUser.isPresent()) {
            Optional<User> sameNickname = database.getUserRepository().findByDisplayName(user.getDisplayName());
            if (sameNickname.isPresent()) {
                return new ResponseEntity<>(new Response(Response.TYPE_CHANGE_NICKNAME_EXISTS), HttpStatus.OK);
            } else {
                database.getUserRepository().save(user);
                return new ResponseEntity<>(new Response(Response.TYPE_CHANGE_NICKNAME_SUCCESS), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new Response(Response.TYPE_AUTH_FAILED), HttpStatus.OK);
        }
    }
}
