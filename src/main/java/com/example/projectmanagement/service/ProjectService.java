package com.example.projectmanagement.service;

import com.example.projectmanagement.modal.Project;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project , Long userId) throws ExecutionControl.UserException;

    List<Project> getProjectByTeam
}
