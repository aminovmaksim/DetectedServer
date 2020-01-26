package com.devian.detected.controllers;

import com.devian.detected.domain.network.Response;
import com.devian.detected.domain.Task;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import com.devian.detected.utils.GsonSerializer;
import com.devian.detected.utils.NetworkManager;
import com.devian.detected.utils.TimeManager;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController()
public class TaskController {

    private Gson gson = GsonSerializer.getInstance().getGson();

    private final Database database;

    public TaskController(Database database) {
        this.database = database;
    }

    @PostMapping(value = "/addTask")
    private ResponseEntity<Response> addTask(
            @RequestHeader(value = "data") String data
    ) {
        String requestData = NetworkManager.getInstance().proceedRequest(data);
        log.info("New Task: " + requestData);

        Task task = gson.fromJson(requestData, Task.class);
        database.getTaskRepository().save(task);

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_ADDED);
    }

    @GetMapping(value = "/getMapTasks")
    private ResponseEntity<Response> getMapTasks() {
        log.info("Map tasks request");

        List<Task> mapTasks = database.getTaskRepository().findAllByTypeAndCompleted(Task.TYPE_MAP, 0);
        String responseData = gson.toJson(mapTasks);

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_SUCCESS, responseData);
    }

    @GetMapping(value = "/getTextTasks")
    protected ResponseEntity<Response> getTextTasks() {
        log.info("Text tasks request");

        List<Task> textTasks = database.getTaskRepository().findAllByTypeAndCompleted(Task.TYPE_TEXT, 0);
        String responseData = gson.toJson(textTasks);

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_SUCCESS, responseData);
    }

    @PostMapping(value = "/scanTag")
    private ResponseEntity<Response> scanTag(
            @RequestHeader(value = "data") String data
    ) {
        String requestData = NetworkManager.getInstance().proceedRequest(data);
        log.info("New tag scanned: " + requestData);

        Task user_task = gson.fromJson(requestData, Task.class);
        Optional<Task> optionalTask = database.getTaskRepository().findByTagId(user_task.getTagId());
        if (!optionalTask.isPresent()) {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_FAILURE);
        }
        Optional<UserStats> optionalUserStats = database.getStatsRepository().findByUid(user_task.getExecutor());
        if (!optionalUserStats.isPresent()) {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_FAILURE);
        }
        UserStats userStats = optionalUserStats.get();
        Task task = optionalTask.get();
        if (task.isCompleted()) {
            return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_ALREADY_COMPLETED);
        }
        userStats.completeTask(task);
        task.setCompleted(1);
        task.setExecutor(userStats.getUid());
        task.setCompletedTime(TimeManager.getCurrentTime());
        database.getTaskRepository().save(task);
        database.getStatsRepository().save(userStats);
        String responseData = gson.toJson(task);

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_COMPLETED, responseData);
    }

    @GetMapping(value = "/getEvent")
    private ResponseEntity<Response> getEvent() {
        log.info("New event request");

        String event = "Стань топ-1 среди всех кулхацкеров до 31 января и получи секретное приглашение";

        return NetworkManager.getInstance().proceedResponse(Response.TYPE_TASK_SUCCESS, event);
    }
}
