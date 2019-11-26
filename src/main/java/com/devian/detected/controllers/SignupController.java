package com.devian.detected.controllers;

import com.devian.detected.domain.Response;
import com.devian.detected.domain.SignUp;
import com.devian.detected.domain.User;
import com.devian.detected.repository.Database;
import com.devian.detected.security.CipherUtility;
import com.devian.detected.utils.RESTHelper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController()
public class SignupController {

    @Autowired
    private CipherUtility cipherUtility;
    @Autowired
    private Database database;

    private Gson gson = new Gson();

    @GetMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> signUp(
            @RequestHeader(value = "PUKey") String PUKey,
            @RequestHeader(value = "userData") String userData
    ) {
        try {

            SignUp signUp = gson.fromJson(
                    CipherUtility.decrypt(userData, CipherUtility.keyPair.getPrivate()),
                    SignUp.class
            );
            UUID uuid = UUID.randomUUID();
            String login = signUp.getLogin();
            String email = signUp.getEmail();

            log.info(uuid.toString());

            Optional<User> optionalUser = database.getUserRepository().findByLogin(login);
            if (optionalUser.isPresent()) {
                return RESTHelper.generateResponse(new Response(-1), PUKey);
            }
            optionalUser = database.getUserRepository().findByEmail(email);
            if (optionalUser.isPresent()) {
                return RESTHelper.generateResponse(new Response(-2), PUKey);
            }

            User user = new User(uuid, login, email);
            database.getUserRepository().save(user);

            return RESTHelper.generateResponse(new Response(1, uuid.toString()), PUKey);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return RESTHelper.generateResponse(new Response(-3, e.getMessage()), PUKey);
        }
    }
}
