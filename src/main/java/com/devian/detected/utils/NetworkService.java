package com.devian.detected.utils;

import com.devian.detected.DetectedApplication;
import com.devian.detected.domain.network.Response;
import com.devian.detected.security.AES256;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NetworkService {

    private static NetworkService mInstance;

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public String proceedRequest(String data) {
        if (DetectedApplication.encryptionEnabled) {
            return AES256.decrypt(data);
        } else {
            return data;
        }
    }

    public ResponseEntity<Response> proceedResponse() {
        return new ResponseEntity<>(new Response(Response.TYPE_DEFAULT), HttpStatus.OK);
    }

    public ResponseEntity<Response> proceedResponse(int responseType) {
        return new ResponseEntity<>(new Response(responseType), HttpStatus.OK);
    }

    public ResponseEntity<Response> proceedResponse(int responseType, String data) {
        if (DetectedApplication.encryptionEnabled) {
            return new ResponseEntity<>(new Response(responseType, AES256.encrypt(data)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(responseType, data), HttpStatus.OK);
        }

    }
}
