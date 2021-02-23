package com.github.aborn.webx.listeners;

import com.github.aborn.webx.modules.tc.TraceRecorder;
import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFrame;
import org.jetbrains.annotations.NotNull;

/**
 * @author aborn
 * @date 2021/02/08 3:48 PM
 */
public class WebxApplicationActivationListener extends UserActionBaseListener implements ApplicationActivationListener {
    public WebxApplicationActivationListener(){}

    @Override
    public void applicationActivated(@NotNull IdeFrame ideFrame) {
        Project project = ideFrame.getProject();
        info("idea active(open). projectName:" + getProjectName(project));
        TraceRecorder.open();
        record();
    }

    @Override
    public void delayedApplicationDeactivated(@NotNull IdeFrame ideFrame) {
        Project project = ideFrame.getProject();
        info("idea non-active(close). projectName:" + getProjectName(project));
        TraceRecorder.close();
    }

    private String getProjectName(Project project) {
        return project == null ? "" : project.getName();
    }
}
