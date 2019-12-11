package com.devian.detected.domain;

import com.devian.detected.security.AES256;

public class Response {

    /*

    0 : default

    ------------ OK ------------

    10 : auth success

    20 : stats exists

    ----------- ERROR ----------

    -20 : stats does not exist

     */

    private int type;
    private String data;

    public Response(int type, String data) {
        this.type = type;
        this.data = AES256.encrypt(data);
    }

    public Response(int type) {
        this.type = type;
        this.data = "";
    }

    public Response() {
        this.type = 0;
        this.data = "";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return AES256.decrypt(data);
    }

    public void setData(String data) {
        this.data = AES256.encrypt(data);
    }
}
