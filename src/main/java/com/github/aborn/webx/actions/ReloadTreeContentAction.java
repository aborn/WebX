package com.github.aborn.webx.actions;

import com.github.aborn.webx.ui.RSNavigatorPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author aborn
 * @date 2021/02/01 2:07 PM
 */
public class ReloadTreeContentAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = CommonDataKeys.PROJECT.getData(e.getDataContext());
        final ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("WebX");

        RSNavigatorPanel panel = (RSNavigatorPanel)toolWindow.getContentManager().getContent(0).getComponent();
        panel.refreshUiContent();
    }
}
