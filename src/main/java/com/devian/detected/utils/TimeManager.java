package com.devian.detected.utils;

import java.sql.Date;

public class TimeManager {
    public static Date getCurrentTime() {
        return new Date(System.currentTimeMillis());
    }
}
