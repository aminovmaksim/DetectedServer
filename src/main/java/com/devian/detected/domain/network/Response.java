package com.devian.detected.domain.network;

public class Response {

    private int type;
    private String data;

    public Response(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public Response(int type) {
        this.type = type;
        this.data = "";
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static final int TYPE_DEFAULT = 0;

    public static final int TYPE_ERROR = -1;

    public static final int TYPE_AUTH_SUCCESS = 10;

    public static final int TYPE_STATS_EXISTS = 20;

    public static final int TYPE_TASK_SUCCESS = 30;
    public static final int TYPE_TASK_ADDED = 31;
    public static final int TYPE_TASK_COMPLETED = 32;

    public static final int TYPE_RANK_SUCCESS = 40;

    public static final int TYPE_CHANGE_NICKNAME_SUCCESS = 50;

    public static final int TYPE_AUTH_FAILED = 10;

    public static final int TYPE_STATS_DOES_NOT_EXIST = -20;

    public static final int TYPE_TASK_FAILURE = -30;
    public static final int TYPE_TASK_ALREADY_COMPLETED = -32;

    public static final int TYPE_RANK_FAILURE = -40;

    public static final int TYPE_CHANGE_NICKNAME_FAILURE = -50;
    public static final int TYPE_CHANGE_NICKNAME_EXISTS = -51;
}
