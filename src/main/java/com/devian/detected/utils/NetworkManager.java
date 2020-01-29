package com.devian.detected.utils;

import com.devian.detected.DetectedApplication;
import com.devian.detected.domain.network.Response;
import com.devian.detected.security.AES256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@SuppressWarnings("unused")
public class NetworkManager {

    private static NetworkManager mInstance;

    public static NetworkManager getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkManager();
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
        Response response = new Response(responseType);
        log.info(GsonSerializer.getInstance().getGson().toJson(response));
        return new ResponseEntity<>(new Response(responseType), HttpStatus.OK);
    }

    public ResponseEntity<Response> proceedResponse(int responseType, String data) {
        Response response = new Response(responseType, data);
        log.info(GsonSerializer.getInstance().getGson().toJson(response));
        if (DetectedApplication.encryptionEnabled) {
            return new ResponseEntity<>(new Response(responseType, AES256.encrypt(data)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(responseType, data), HttpStatus.OK);
        }

    }
}
