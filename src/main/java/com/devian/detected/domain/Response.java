package com.devian.detected.domain;

public class Response {

    /*

    0 : default

    ------------ OK ------------

    1 : sign up success, return uuid

    ----------- ERROR ----------

    -1 : sign up failure, login exist
    -2 : sign up failure, email exist
    -3 : sign up failure

     */


    private int type;
    private String info;

    public Response(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public Response(int type) {
        this.type = type;
        switch (type) {
            case 1:
                break;
            case -1:
                info = "sign up failure, login exist";
                break;
            case -2:
                info = "sign up failure, email exist";
                break;
            case -3:
                info = "sign up failure";
                break;
            default:
                info = "OK";
        }
    }

    public Response() {
        this.type = 0;
        this.info = "";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
