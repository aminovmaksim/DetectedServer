package com.devian.detected.controllers;

import com.devian.detected.domain.RankRow;
import com.devian.detected.domain.network.Response;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import com.devian.detected.services.RankingService;
import com.devian.detected.utils.GsonSerializer;
import com.devian.detected.utils.NetworkManager;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class StatsController {

    private Gson gson = GsonSerializer.getInstance().getGson();

    private final Database database;

    public StatsController(Database database) {
        this.database = database;
    }

    @GetMapping(value = "/getUserStats", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> getStats(
            @RequestHeader(value = "data") String data
    ) {
        String uid = NetworkManager.getInstance().proceedRequest(data);
        log.info("New stats request: " + uid);

        Optional<UserStats> optionalUserStats = database.getStatsRepository().findByUid(uid);
        if (!optionalUserStats.isPresent()) {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_STATS_DOES_NOT_EXIST);
        }
        String userStats = gson.toJson(optionalUserStats.get());

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_STATS_EXISTS, userStats);
    }

    @GetMapping(value = "/getRankTop10", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> getRankTop10() {
        log.info("New top10 request");
        List<RankRow> rankRows = RankingService.top10;
        String responseData = gson.toJson(rankRows);

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_RANK_SUCCESS, responseData);
    }

    @GetMapping(value = "/getSelfRank", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> getPersonalRank(
            @RequestHeader(value = "data") String data
    ) {
        String uid = NetworkManager.getInstance().proceedRequest(data);
        log.info("New rank request: " + uid);

        Optional<RankRow> optionalRankRow = database.getRankRepository().findByUid(uid);
        return optionalRankRow
                .map(rankRow -> NetworkManager.getInstance().proceedResponse(Response.TYPE_RANK_SUCCESS, gson.toJson(rankRow)))
                .orElseGet(() -> NetworkManager.getInstance().proceedResponse(Response.TYPE_RANK_FAILURE));
    }
}
