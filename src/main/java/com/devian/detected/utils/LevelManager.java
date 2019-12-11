package com.devian.detected.utils;

public class LevelManager {

    /*
    0: 0 - 50
    1: 51 - 100
    2: 101 - 150
    3: 151 - 250
    4: 251 - 400
    5: 401 - 600
    6: 601 - 900
    ...
     */
    public static int getLevelByPoints(long points) {
        if (points <= 50)
            return 0;
        long p = 50;
        int l = 0;
        while (p < points) {
            p = roundToUpperFifty(Math.round(p*1.5));
            l++;
        }
        return l;
    }

    private static long roundToUpperFifty(long n) {
        if (n % 50 == 0)
            return n;
        else
            return 50 * (n/50 + 1);
    }
}
