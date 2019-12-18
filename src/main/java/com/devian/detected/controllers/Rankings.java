package com.devian.detected.controllers;

import com.devian.detected.domain.RankRow;
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

@Slf4j
@Configuration
@EnableScheduling
public class Rankings {

    private final Database database;

    public static List<RankRow> top10 = new ArrayList<>();

    public Rankings(Database database) {
        this.database = database;
    }

    @Scheduled(fixedDelay = 300000)
    public void proceedRank() {
        log.info("updating the rank");
        top10.clear();

        List<UserStats> userStats = database.getStatsRepository().findAll();
        Collections.sort(userStats);
        List<RankRow> rankRows = new ArrayList<>();
        for (int i = 0; i < userStats.size(); i++) {
            RankRow rankRow = new RankRow(userStats.get(i).getUid(), i + 1);
            rankRows.add(rankRow);
            if (i < 10) {
                top10.add(rankRow);
            }
        }
        database.getRankRepository().saveAll(rankRows);

    }
}
