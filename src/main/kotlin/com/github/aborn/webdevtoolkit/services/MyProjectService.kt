package com.github.aborn.webdevtoolkit.services

import com.github.aborn.webdevtoolkit.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
