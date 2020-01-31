package com.devian.detected.controllers;

import com.devian.detected.domain.network.Response;
import com.devian.detected.domain.tasks.GeoTask;
import com.devian.detected.domain.tasks.GeoTextTask;
import com.devian.detected.domain.tasks.Task;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import com.devian.detected.utils.GsonSerializer;
import com.devian.detected.utils.NetworkManager;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController()
@SuppressWarnings("unused")
public class TaskController {

    private Gson gson = GsonSerializer.getInstance().getGson();

    private final Database database;

    public TaskController(Database database) {
        this.database = database;
    }

    @GetMapping(value = "/getMapTasks")
    private ResponseEntity<Response> getMapTasks() {
        log.info("Map tasks request");

        List<GeoTask> mapTasks = database.getGeoTaskRepository().findAllByCompleted(false);
        String responseData = gson.toJson(mapTasks);

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_SUCCESS, responseData);
    }

    @GetMapping(value = "/getTextTasks")
    protected ResponseEntity<Response> getTextTasks() {
        log.info("Text tasks request");

        List<GeoTextTask> textTasks = database.getGeoTextTaskRepository().findAllByCompleted(false);
        String responseData = gson.toJson(textTasks);

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_SUCCESS, responseData);
    }

    @PostMapping(value = "/scanGeoTag")
    private ResponseEntity<Response> scanGeoTag(
            @RequestHeader(value = "data") String data
    ) {
        String requestData = NetworkManager.getInstance().proceedRequest(data);
        log.info("New geo tag scanned: " + requestData);

        GeoTask user_task = gson.fromJson(requestData, GeoTask.class);
        Optional<GeoTask> optionalGeoTag = database.getGeoTaskRepository().findByTagId(user_task.getTagId());
        if (!optionalGeoTag.isPresent()) {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_FAILURE);
        }
        if (optionalGeoTag.get().isCompleted()) {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_ALREADY_COMPLETED);
        }
        GeoTask task = proceedTask(optionalGeoTag.get(), user_task.getExecutor());
        if (task != null) {
            database.getGeoTaskRepository().save(task);
            String responseData = gson.toJson(task);
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_COMPLETED, responseData);
        }
        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_FAILURE);
    }

    @PostMapping(value = "/scanGeoTextTag")
    private ResponseEntity<Response> scanGeoTextTag(
            @RequestHeader(value = "data") String data
    ) {
        String requestData = NetworkManager.getInstance().proceedRequest(data);
        log.info("New geo text tag scanned: " + requestData);

        GeoTextTask user_task = gson.fromJson(requestData, GeoTextTask.class);
        Optional<GeoTextTask> optionalGeoTextTask = database.getGeoTextTaskRepository().findByTagId(user_task.getTagId());
        if (!optionalGeoTextTask.isPresent()) {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_FAILURE);
        }
        if (optionalGeoTextTask.get().isCompleted()) {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_ALREADY_COMPLETED);
        }
        GeoTextTask task = proceedTask(optionalGeoTextTask.get(), user_task.getExecutor());
        if (task != null) {
            log.info("============== " + gson.toJson(task));
            database.getGeoTextTaskRepository().save(task);
            String responseData = gson.toJson(task);
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_COMPLETED, responseData);
        }
        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_FAILURE);
    }

    private <T extends Task> T proceedTask(T task, String executor) {
        Optional<UserStats> optionalUserStats = database.getStatsRepository().findByUid(executor);
        if (!optionalUserStats.isPresent()) {
            return null;
        }

        UserStats userStats = optionalUserStats.get();
        userStats.completeTask(task);
        database.getStatsRepository().save(userStats);

        task.setCompleted(true);
        task.setExecutor(userStats.getUid());
        task.setCompletedTime(new Date());
        return task;
    }

    @GetMapping(value = "/getEvent")
    private ResponseEntity<Response> getEvent() {
        log.info("New event request");

        String event = "Стань топ-1 среди всех кулхацкеров до 31 января и получи секретное приглашение";

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_SUCCESS, event);
    }
}
