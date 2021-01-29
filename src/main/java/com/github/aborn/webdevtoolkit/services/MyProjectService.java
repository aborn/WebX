package com.github.aborn.webdevtoolkit.services;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;

/**
 * @author aborn
 * @date 2021/01/29 2:44 PM
 */
@Service
public final class MyProjectService {
    private final Project myProject;

    public MyProjectService(Project project) {
        myProject = project;
    }
}
