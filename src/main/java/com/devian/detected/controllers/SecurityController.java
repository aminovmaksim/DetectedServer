package com.devian.detected.controllers;

import com.devian.detected.domain.Response;
import com.devian.detected.security.CipherUtility;
import com.devian.detected.utils.RESTHelper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private CipherUtility cipherUtility;

    private Gson gson = new Gson();

    @GetMapping(value = "/getPUKey", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> getPUKey() {
        log.info("getPUKey request");
        return new ResponseEntity<>(
                gson.toJson(new Response(0, CipherUtility.encodeKey(CipherUtility.keyPair.getPublic()))),
                        HttpStatus.OK);
    }

}
