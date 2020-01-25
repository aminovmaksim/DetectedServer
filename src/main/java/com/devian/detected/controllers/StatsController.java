package com.devian.detected.controllers;

import com.devian.detected.domain.RankRow;
import com.devian.detected.domain.Response;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import com.devian.detected.security.AES256;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController()
public class StatsController {

    private Gson gson = new Gson();

    private final Database database;

    public StatsController(Database database) {
        this.database = database;
    }

    @GetMapping(value = "/getStats", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> getStats(
            @RequestHeader(value = "data") String data
    ) {
        //decrypt incoming data
        String uid = AES256.decrypt(data);
        log.info("New stats request: " + uid);

        Optional<UserStats> optionalUserStats = database.getStatsRepository().findByUid(uid);
        if (!optionalUserStats.isPresent()) {
            return new ResponseEntity<>(new Response(Response.TYPE_STATS_DOES_NOT_EXIST), HttpStatus.OK);
        }

        String userStats = gson.toJson(optionalUserStats.get());
        Response response = new Response(Response.TYPE_STATS_EXISTS, userStats);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getRankTop10", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> getRankTop10() {
        log.info("New top10 request");
        List<RankRow> rankRows = Rankings.top10;
        return new ResponseEntity<>(new Response(Response.TYPE_RANK_SUCCESS, gson.toJson(rankRows)), HttpStatus.OK);
    }

    @GetMapping(value = "/getPersonalRank", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> getPersonalRank(
            @RequestHeader(value = "data") String data
    ) {
        //decrypt incoming data
        String uid = AES256.decrypt(data);
        log.info("New rank request: " + uid);

        Optional<RankRow> optionalRankRow = database.getRankRepository().findByUid(uid);
        return optionalRankRow
                .map(rankRow -> new ResponseEntity<>(new Response(Response.TYPE_RANK_SUCCESS, gson.toJson(rankRow)), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Response(Response.TYPE_RANK_FAILURE), HttpStatus.OK));
    }
}
