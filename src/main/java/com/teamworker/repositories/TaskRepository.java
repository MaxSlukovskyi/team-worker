package com.teamworker.repositories;

import com.teamworker.models.Project;
import com.teamworker.models.Task;
import com.teamworker.models.User;
import com.teamworker.models.enums.TaskStage;
import com.teamworker.models.enums.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getAllByProject(Project project);

    List<Task> getAllByAssigneeId(Long id);

    Integer countTasksByAssigneeIdAndStage(Long id, TaskStage stage);

    Integer countTasksByAssigneeIdAndType(Long id, TaskType type);

    List<Task> getAllByAssigneeIdAndStageOrderByDueTime(Long id, TaskStage stage);

    List<Task> getAllByStageOrderByDueTime(TaskStage stage);

    List<Task> getAllByStageAndProject_ManagerOrderByDueTime(TaskStage stage, User manager);

    List<Task> getAllByAssigneeIdAndCreateTimeBetween(Long assignee_id, Timestamp time1, Timestamp time2);
}
