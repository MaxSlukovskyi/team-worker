package com.teamworker.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamworker.models.Project;
import com.teamworker.models.Task;
import com.teamworker.models.User;
import com.teamworker.models.enums.Priority;
import com.teamworker.models.enums.TaskStage;
import com.teamworker.models.enums.TaskType;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private Timestamp createTime;
    private Timestamp dueTime;
    private Timestamp lastEditTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private User assignee;
    private User creator;
    private Project project;
    private Priority priority;
    private TaskStage stage;
    private TaskType type;

    public Task toTask(TaskDto taskDto) {
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setCreateTime(createTime);
        task.setDueTime(dueTime);
        task.setLastEditTime(lastEditTime);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        task.setAssignee(assignee);
        task.setCreator(creator);
        task.setProject(project);
        task.setPriority(priority);
        task.setStage(stage);
        task.setType(type);
        return task;
    }

    public static TaskDto fromTask(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setCreateTime(task.getCreateTime());
        taskDto.setDueTime(task.getDueTime());
        taskDto.setLastEditTime(task.getLastEditTime());
        taskDto.setStartTime(task.getStartTime());
        taskDto.setEndTime(task.getEndTime());
        taskDto.setAssignee(task.getAssignee());
        taskDto.setCreator(task.getCreator());
        taskDto.setProject(task.getProject());
        taskDto.setPriority(task.getPriority());
        taskDto.setStage(task.getStage());
        taskDto.setType(task.getType());
        return taskDto;
    }
}
