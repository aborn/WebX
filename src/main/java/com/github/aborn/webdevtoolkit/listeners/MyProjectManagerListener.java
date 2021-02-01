package com.github.aborn.webdevtoolkit.listeners;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * @author aborn
 * @date 2021/01/29 2:56 PM
 */
public class MyProjectManagerListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        // Ensure this isn't part of testing
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }
        //Messages.showMessageDialog(project, "打开！", project.getName(), Messages.getInformationIcon());
    }

    /**
     * Invoked on project close.
     *
     * @param project closing project
     */
    @Override
    public void projectClosed(@NotNull Project project) {
        // Ensure this isn't part of testing
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }
        //Messages.showMessageDialog(project, "关闭！", project.getName(), Messages.getInformationIcon());
    }

}
