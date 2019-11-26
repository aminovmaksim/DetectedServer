package com.devian.detected.utils;

import com.devian.detected.domain.Response;
import com.devian.detected.security.CipherUtility;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.PublicKey;


public class RESTHelper {

    private static Gson gson = new Gson();

    public static ResponseEntity<String> generateResponse(Response response, String key) {
        try {
            PublicKey clientPublicKey = CipherUtility.decodePublicKey(key);

            return new ResponseEntity<>(
                    CipherUtility.encrypt(
                            gson.toJson(response),
                            clientPublicKey),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    "Error :(",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
