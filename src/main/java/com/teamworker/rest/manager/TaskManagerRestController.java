package com.teamworker.rest.manager;

import com.teamworker.dtos.TaskDto;
import com.teamworker.models.Task;
import com.teamworker.models.User;
import com.teamworker.models.enums.TaskStage;
import com.teamworker.services.TaskService;
import com.teamworker.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "/api/v1/manager/task")
@Tag(name = "/api/v1/manager/task", description = "Контролер для керування завданнями (manager)")
public class TaskManagerRestController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskManagerRestController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping(value = "get/all")
    @Operation(summary = "Отримати всі завдання доступних проектів (manager)")
    public ResponseEntity<List<TaskDto>> getAll() throws ParseException {

        List<Task> tasks = taskService.getAllByManager(userService.getCurrentUser().getId());

        if (tasks == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<TaskDto> result = new ArrayList<>();

        for (Task task : tasks) {
            result.add(TaskDto.fromTask(task));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Оновити завдання")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable(value = "id") Long id,
            @RequestBody TaskDto taskDto) throws ParseException {

        Task task = taskService.update(id, taskDto.toTask());

        if(task == null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        TaskDto result = TaskDto.fromTask(task);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/get/all/{stage}")
    @Operation(summary = "Отримати всі завдання за стадією для авторизованого менеджера")
    public ResponseEntity<List<TaskDto>> getAllByStage(@PathVariable(value = "stage") String stageName) throws ParseException {
        List<Task> tasks = taskService.getAllByStageForManager(stageName, userService.getCurrentUser().getId());
        List<TaskDto> result = new ArrayList<>();

        for (Task task : tasks) {
            result.add(TaskDto.fromTask(task));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/get/stats/ontime/{id}")
    @Operation(summary = "Отримати відсоток зроблених вчасно завдань певного користувача")
    public ResponseEntity<Integer> getPercentageOfMadeOnTime(@PathVariable(value = "id") Long id) {
        Integer percentage = taskService.getPercentageOfCompletedOnTime(id);
        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }

    @GetMapping(value = "/get/stats/number/{id}/{stage}")
    @Operation(summary = "Отримати кількість завдань певного користувача за стадією")
    public ResponseEntity<Integer> getNumberByUserAndStage(@PathVariable(value = "id") Long id,
                                                           @PathVariable(value = "stage") String stageName) {
        Integer number = taskService.getNumberByAssigneeAndStage(id, stageName);
        return new ResponseEntity<>(number, HttpStatus.OK);
    }

    @GetMapping(value = "/get/stats/average/time/{id}")
    @Operation(summary = "Отримати середню тривалість виконання завдання певного користувача")
    public ResponseEntity<String> getAverageTimeOfCompletingByAssignee(@PathVariable(value = "id") Long id) {
        String time = taskService.getAverageTimeOfCompletingByAssignee(userService.getCurrentUser().getId());
        return new ResponseEntity<>(time, HttpStatus.OK);
    }

    @GetMapping(value = "/get/stats/best/month")
    @Operation(summary = "Отримати найбільшу кількість виконаних завдання за місяць певного користувача")
    public ResponseEntity<Integer> getNumberOfMostProductiveMonthByAssignee(@PathVariable(value = "id") Long id) throws JSONException {
        Integer number = taskService.getNumberOfMostProductiveMonthByAssignee(id);
        return new ResponseEntity<>(number, HttpStatus.OK);
    }
}
