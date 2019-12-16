package com.devian.detected.controllers;

import com.devian.detected.domain.User;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import org.hibernate.mapping.Collection;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;
import java.util.List;

public class Rankings {

    private final Database database;

    public Rankings(Database database) {
        this.database = database;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 600000)
    public void proceedRank() {
        List<UserStats> userStats = database.getStatsRepository().findAll();
        Collections.sort(userStats);
        for (UserStats us : userStats) {
            
        }

    }
}
