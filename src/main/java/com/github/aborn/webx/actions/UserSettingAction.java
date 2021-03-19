package com.github.aborn.webx.actions;

import com.github.aborn.webx.ui.Settings;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author aborn
 * @date 2021/03/19 7:38 PM
 */
public class UserSettingAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        Settings popup = new Settings(project);
        popup.show();
    }
}
