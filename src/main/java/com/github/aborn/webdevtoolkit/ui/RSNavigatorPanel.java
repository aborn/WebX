package com.github.aborn.webdevtoolkit.ui;


import com.github.aborn.webdevtoolkit.commons.RestServiceManager;
import com.github.aborn.webdevtoolkit.datatypes.RestServiceItem;
import com.github.aborn.webdevtoolkit.datatypes.RestServiceModule;
import com.github.aborn.webdevtoolkit.datatypes.nodes.ModuleNode;
import com.github.aborn.webdevtoolkit.datatypes.nodes.ProjectNode;
import com.github.aborn.webdevtoolkit.datatypes.nodes.ServiceNode;
import com.github.aborn.webdevtoolkit.utils.RSDataKeys;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.treeStructure.SimpleTree;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aborn
 * @date 2021/01/29 4:09 PM
 */
public class RSNavigatorPanel extends SimpleToolWindowPanel implements DataProvider {

    private final SimpleTree myTree;
    private final Project myProject;

    private Splitter servicesContentPaneSplitter;
    private final JTextArea defaultUnderView = new JTextArea(" json format textarea ");


    public RSNavigatorPanel(Project project, SimpleTree tree) {
        super(true, true);

        myTree = tree;
        myProject = project;

        final ActionManager actionManager = ActionManager.getInstance();
        ActionToolbar actionToolbar = actionManager.createActionToolbar("RestToolkit Navigator Toolbar",
                (DefaultActionGroup) actionManager.getAction("Toolkit.NavigatorActionsToolbar"), true);
        setToolbar(actionToolbar.getComponent());
        Color gray = new Color(36, 38, 39);
        myTree.setBorder(BorderFactory.createLineBorder(gray));
        JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(myTree);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED));


        servicesContentPaneSplitter = new Splitter(true, .5f);
        servicesContentPaneSplitter.setShowDividerControls(true);
        servicesContentPaneSplitter.setDividerWidth(10);
        servicesContentPaneSplitter.setBorder(BorderFactory.createLineBorder(Color.RED));

        // 显示服务列表
        servicesContentPaneSplitter.setFirstComponent(scrollPane);
        setContent(servicesContentPaneSplitter);
    }

    public void refreshUIContent() {
        ProjectNode projectNode = new ProjectNode();
        List<RestServiceModule> moduleNodeList = RestServiceManager.buildRestServiceData(myProject);
        DefaultMutableTreeNode rootNode = buildProjectRootNode(moduleNodeList, projectNode);
        TreeModel treeModel = new DefaultTreeModel(rootNode);
        myTree.setModel(treeModel);
    }

    private DefaultMutableTreeNode buildProjectRootNode(List<RestServiceModule> moduleNodeList, ProjectNode projectNode) {
        DefaultMutableTreeNode projectRootNode = new DefaultMutableTreeNode(projectNode);
        for (RestServiceModule module : moduleNodeList) {
            projectRootNode.add(buildModuleNode(module, projectNode));
        }
        return projectRootNode;
    }

    private DefaultMutableTreeNode buildModuleNode(RestServiceModule restServiceModule, ProjectNode projectNode) {
        List<RestServiceItem> restServiceItems = restServiceModule.getServiceItems();
        if (restServiceItems == null || restServiceItems.size() == 0) {
            return null;
        }

        ModuleNode moduleNode = new ModuleNode(projectNode, restServiceModule);
        projectNode.addModuleNode(moduleNode);

        DefaultMutableTreeNode moduleRoot = new DefaultMutableTreeNode(moduleNode);
        for (RestServiceItem item : restServiceItems) {
            moduleRoot.add(new DefaultMutableTreeNode(new ServiceNode(moduleNode, item)));
        }
        return moduleRoot;
    }

    /*
    private Collection<? extends RestServiceStructure.BaseSimpleNode> getSelectedNodes(Class<RestServiceStructure.BaseSimpleNode> aClass) {
        return RestServiceStructure.getSelectedNodes(myTree, aClass);
    }*/

    @Override
    @Nullable
    public Object getData(@NonNls String dataId) {

        if (RSDataKeys.SERVICE_ITEMS.is(dataId)) {
            return extractServices();
        }

        return super.getData(dataId);
    }

    private List<RestServiceItem> extractServices() {
        List<RestServiceItem> result = new ArrayList<>();

        /*
        Collection<? extends RestServiceStructure.BaseSimpleNode> selectedNodes = getSelectedNodes(RestServiceStructure.BaseSimpleNode.class);
        for (RestServiceStructure.BaseSimpleNode selectedNode : selectedNodes) {
            if (selectedNode instanceof RestServiceStructure.ServiceNode) {
                result.add(((RestServiceStructure.ServiceNode) selectedNode).myServiceItem);
            }
        }*/
        return result;
    }

}


