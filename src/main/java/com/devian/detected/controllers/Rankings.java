package com.devian.detected.controllers;

import com.devian.detected.domain.RankRow;
import com.devian.detected.domain.User;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@EnableScheduling
public class Rankings {

    private final Database database;

    public static List<RankRow> top10 = new ArrayList<>();

    public Rankings(Database database) {
        this.database = database;
    }

    @Scheduled(fixedDelay = 60000)
    public void proceedRank() {
        log.info("updating the rank");
        top10.clear();

        List<UserStats> userStats = database.getStatsRepository().findAll();
        List<User> users = database.getUserRepository().findAll();

        Collections.sort(userStats);
        List<RankRow> rankRows = new ArrayList<>();
        for (int i = 0; i < userStats.size(); i++) {
            UserStats us = userStats.get(i);
            String uid = us.getUid();
            Optional<User> optionalUser = database.getUserRepository().findByUid(uid);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                RankRow rankRow = new RankRow(uid, i + 1, user.getDisplayName(), us.getPoints());
                rankRows.add(rankRow);
                if (i < 10) {
                    top10.add(rankRow);
                }
            }
        }
        database.getRankRepository().saveAll(rankRows);

    }
}
