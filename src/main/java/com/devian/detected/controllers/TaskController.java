package com.devian.detected.controllers;

import com.devian.detected.domain.Response;
import com.devian.detected.domain.Task;
import com.devian.detected.domain.UserStats;
import com.devian.detected.repository.Database;
import com.devian.detected.security.AES256;
import com.devian.detected.utils.TimeManager;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    private Gson gson = new Gson();

    private final Database database;

    public TaskController(Database database) {
        this.database = database;
    }

    @PostMapping(value = "/addTask")
    private ResponseEntity<Response> addTask(
            @RequestHeader(value = "data") String data
    ) {
        String userData = AES256.decrypt(data);
        log.info("New Task: " + userData);

        Task task = gson.fromJson(userData, Task.class);
        database.getTaskRepository().save(task);

        return new ResponseEntity<>(new Response(Response.TYPE_TASK_ADDED), HttpStatus.OK);
    }

    @GetMapping(value = "/getMapTasks")
    private ResponseEntity<Response> getMapTasks() {
        log.info("Map tasks request");
        List<Task> mapTasks = database.getTaskRepository().findAllByTypeAndCompleted(Task.TYPE_MAP, 0);
        String response_data = gson.toJson(mapTasks);
        return new ResponseEntity<>(new Response(Response.TYPE_TASK_SUCCESS, response_data), HttpStatus.OK);
    }

    @GetMapping(value = "/getTextTasks")
    protected ResponseEntity<Response> getTextTasks() {
        log.info("Text tasks request");
        List<Task> textTasks = database.getTaskRepository().findAllByTypeAndCompleted(Task.TYPE_TEXT, 0);
        String response_data = gson.toJson(textTasks);
        return new ResponseEntity<>(new Response(Response.TYPE_TASK_SUCCESS, response_data), HttpStatus.OK);
    }

    @PostMapping(value = "/scanTag")
    private ResponseEntity<Response> scanTag(
            @RequestHeader(value = "data") String data
    ) {
        String userData = AES256.decrypt(data);
        log.info("New tag scanned: " + userData);

        Task user_task = gson.fromJson(userData, Task.class);

        Optional<Task> optionalTask = database.getTaskRepository().findByTagId(user_task.getTagId());
        if (!optionalTask.isPresent()) {
            return new ResponseEntity<>(new Response(Response.TYPE_TASK_FAILURE), HttpStatus.OK);
        }
        Optional<UserStats> optionalUserStats = database.getStatsRepository().findByUid(user_task.getExecutor());
        if (!optionalUserStats.isPresent()) {
            return new ResponseEntity<>(new Response(Response.TYPE_TASK_FAILURE), HttpStatus.OK);
        }
        UserStats userStats = optionalUserStats.get();
        Task task = optionalTask.get();
        if (task.isCompleted()) {
            return new ResponseEntity<>(new Response(Response.TYPE_TASK_ALREADY_COMPLETED), HttpStatus.OK);
        }

        userStats.completeTask(task);
        task.setCompleted(1);
        task.setExecutor(userStats.getUid());
        task.setCompletedTime(TimeManager.getCurrentTime());

        database.getTaskRepository().save(task);
        database.getStatsRepository().save(userStats);

        String res = gson.toJson(task);

        return new ResponseEntity<>(new Response(Response.TYPE_TASK_COMPLETED, res), HttpStatus.OK);
    }

    @GetMapping(value = "/getEvent")
    private ResponseEntity<Response> getEvent() {
        log.info("New event request");
        String event = "Стань топ-1 среди всех кулхацкеров до 31 января и получи секретное приглашение";
        return new ResponseEntity<>(new Response(Response.TYPE_TASK_SUCCESS, event), HttpStatus.OK);
    }
}
