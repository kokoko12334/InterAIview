package org.example.demo.services;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;

@Service
public class MyProjectService {
    private final Project project;

    public MyProjectService(Project project) {
        this.project = project;
    }

    public String getProjectName() {
        return project.getName();
    }
}
