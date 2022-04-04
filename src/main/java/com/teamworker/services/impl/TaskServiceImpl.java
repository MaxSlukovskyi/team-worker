package com.teamworker.services.impl;

import com.teamworker.models.*;
import com.teamworker.repositories.RoleRepository;
import com.teamworker.repositories.TaskRepository;
import com.teamworker.services.TaskService;
import com.teamworker.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    public Task add(Task task) {
        log.info("IN add - {} task added", task.getName());
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAll() {
        User currentUser = userService.getCurrentUser();
        List<Position> currentUserPositions = currentUser.getPosition();

        List<Task> tasks = new ArrayList<>();
        for (Position position : currentUserPositions) {
            tasks.addAll(this.getAllByProject(position.getProject()));
        }
        return tasks;
    }

    @Override
    public Task update(Long id, Task task) {
        Task foundTask = taskRepository.findById(id).orElse(null);
        if(foundTask == null) {
            return null;
        }
        if (foundTask.getCreator() != userService.getCurrentUser() ||
                !(userService.isAdmin(userService.getCurrentUser()) &&
                        userService.isAdminOfProject(userService.getCurrentUser(), foundTask.getProject()))) {
            return null;
        }

        foundTask.setName(task.getName());
        foundTask.setDescription(task.getDescription());
        foundTask.setDueTime(task.getDueTime());
        foundTask.setStartTime(task.getStartTime());
        foundTask.setEndTime(task.getEndTime());
        foundTask.setAssignee(task.getAssignee());
        foundTask.setProject(task.getProject());
        foundTask.setPriority(task.getPriority());
        foundTask.setStage(task.getStage());
        foundTask.setType(task.getType());

        log.info("IN update - {} task updated", task.getId());

        return taskRepository.save(foundTask);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Task> getAllByProject(Project project) {
        List<Task> tasks = taskRepository.getAllByProject(project);
        log.info("IN getAllByProject - {} tasks updated", tasks.size());
        return tasks;
    }

}
