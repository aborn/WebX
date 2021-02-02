package com.github.aborn.webdevtoolkit.ui;

import com.github.aborn.webdevtoolkit.utils.ToolkitUtil;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.treeStructure.SimpleTree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.net.URL;

/**
 * @author aborn
 * @date 2021/01/29 4:09 PM
 */
public class RSNavigator implements ToolWindowFactory, DumbAware {
    private static final URL SYNC_ICON_URL = RSNavigator.class.getResource("/actions/refresh.png");

    private SimpleTree myTree;
    protected Project myProject;

    /**
     * Create the tool window content.
     *
     * @param project    current project
     * @param toolWindow current tool window
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        toolWindow.setStripeTitle("WebX");
        ContentManager toolWindowContentManager = toolWindow.getContentManager();
        myProject = project;
        initTree();

        final RSNavigatorPanel panel = new RSNavigatorPanel(myProject, myTree);
        final ContentFactory contentFactory = ServiceManager.getService(ContentFactory.class);
        final Content content = contentFactory.createContent(panel, "", false);
        toolWindowContentManager.addContent(content);
        toolWindowContentManager.setSelectedContent(content, false);

        // 当应用加载完成后，更新UIContents.
        DumbService.getInstance(project).smartInvokeLater(new Runnable() {
            @Override
            public void run() {
                panel.refreshUIContent();
            }
        });

    }


    private void initTree() {
        myTree = new SimpleTree() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                final JLabel myLabel = new JLabel(
                        RestfulToolkitBundle.message("toolkit.navigator.nothing.to.display", ToolkitUtil.formatHtmlImage(SYNC_ICON_URL)));


                if (myProject.isInitialized()) {
                    return;
                }

                myLabel.setFont(getFont());
                myLabel.setBackground(getBackground());
                myLabel.setForeground(getForeground());
                Rectangle bounds = getBounds();
                Dimension size = myLabel.getPreferredSize();
                myLabel.setBounds(0, 0, size.width, size.height);

                int x = (bounds.width - size.width) / 2;
                Graphics g2 = g.create(bounds.x + x, bounds.y + 20, bounds.width, bounds.height);
                try {
                    myLabel.paint(g2);
                } finally {
                    g2.dispose();
                }
            }
        };
        myTree.getEmptyText().clear();

        myTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

}
