package com.teamworker.models;

import com.teamworker.models.enums.ProjectStage;
import com.teamworker.models.enums.ProjectType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name")
    private String name;

    @Column(name = "create_time")
    private Date createTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_stage")
    private ProjectStage projectStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type")
    private ProjectType projectType;

    @ManyToMany(mappedBy = "projects")
    private List<User> users;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
