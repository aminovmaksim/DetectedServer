package com.devian.detected.controllers;

import com.devian.detected.domain.network.Response;
import com.devian.detected.domain.User;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import com.devian.detected.utils.GsonSerializer;
import com.devian.detected.utils.NetworkManager;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@SuppressWarnings("unused")
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
        String requestData = NetworkManager.getInstance().proceedRequest(data);
        log.info("New Auth: " + requestData);

        User user = gson.fromJson(requestData, User.class);

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

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_AUTH_SUCCESS);
    }

    @GetMapping(value = "/getUserInfo")
    private ResponseEntity<Response> getUserInfo(
            @RequestHeader(value = "data") String data
    ) {
        String requestData = NetworkManager.getInstance().proceedRequest(data);
        log.info("New getUserInfo request: " + requestData);

        Optional<User> optionalUser = database.getUserRepository().findByUid(requestData);
        if (optionalUser.isPresent()) {
            String response = gson.toJson(optionalUser.get());
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_AUTH_SUCCESS, response);
        } else {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_AUTH_FAILED);
        }
    }

    @PostMapping(value = "/changeNickname")
    private ResponseEntity<Response> changeNickname(
            @RequestHeader(value = "data") String data
    ) {
        String requestData = NetworkManager.getInstance().proceedRequest(data);
        log.info("New changeNickname request: " + requestData);

        User user = gson.fromJson(requestData, User.class);

        Optional<User> optionalUser = database.getUserRepository().findByUid(user.getUid());
        if (optionalUser.isPresent()) {
            Optional<User> sameNickname = database.getUserRepository().findByDisplayName(user.getDisplayName());
            if (sameNickname.isPresent()) {
                return NetworkManager.getInstance().proceedResponse(Response.TYPE_CHANGE_NICKNAME_EXISTS);
            } else {
                database.getUserRepository().save(user);
                String response = gson.toJson(user);
                return NetworkManager.getInstance().proceedResponse(Response.TYPE_CHANGE_NICKNAME_SUCCESS, response);
            }
        } else {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_CHANGE_NICKNAME_FAILURE);
        }
    }
}
