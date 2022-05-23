package com.teamworker.rest.manager;

import com.teamworker.dtos.ProjectDto;
import com.teamworker.models.Project;
import com.teamworker.models.User;
import com.teamworker.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "/api/v1/manager/projects")
@Tag(name = "/api/v1/manager/projects", description = "Контролер для керування проектами (manager)")
public class ProjectManagerRestController {

    private final ProjectService projectService;

    @Autowired
    ProjectManagerRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/get/all/{id}")
    @Operation(summary = "Отримати всі проекти за менеджером")
    public ResponseEntity<List<ProjectDto>> getAllByManager(@PathVariable(value = "id") Long id) {
        List<Project> projects = projectService.getAllByManager(id);
        List<ProjectDto> result = projects.stream().map(ProjectDto::fromProject).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
