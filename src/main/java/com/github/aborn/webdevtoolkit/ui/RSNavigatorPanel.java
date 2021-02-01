package com.github.aborn.webdevtoolkit.ui;


import com.github.aborn.webdevtoolkit.datatypes.RestServiceItem;
import com.github.aborn.webdevtoolkit.datatypes.RestServiceModule;

import com.github.aborn.webdevtoolkit.datatypes.nodes.ModuleNode;
import com.github.aborn.webdevtoolkit.datatypes.nodes.ProjectNode;
import com.github.aborn.webdevtoolkit.datatypes.nodes.ServiceNode;
import com.github.aborn.webdevtoolkit.utils.RSDataKeys;
import com.github.aborn.webdevtoolkit.zhaow.RestServiceDataManager;
import com.github.aborn.webdevtoolkit.zhaow.RestServiceProject;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void refresh() {
        ProjectNode projectNode = new ProjectNode();
        projectNode.updateServiceCount(5);
        DefaultMutableTreeNode projectRootNode = new DefaultMutableTreeNode(projectNode);
        List<RestServiceItem> serviceItems = new ArrayList<>();

        RestServiceItem restServiceItem = new RestServiceItem("/payment/charge");
        serviceItems.add(restServiceItem);

        // 第一个module节点
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RestServiceModule restServiceModule = new RestServiceModule("payment-recharge-web" + simpleDateFormat.format(new Date()), serviceItems);
        DefaultMutableTreeNode moduleNode = convert(restServiceModule, projectNode);

        // 第二个节点
        RestServiceModule restServiceModule2 = new RestServiceModule("payment-recharge-web2", serviceItems);
        DefaultMutableTreeNode moduleNode2 = convert(restServiceModule2, projectNode);

        List<RestServiceProject> projects = RestServiceDataManager.buildRestServiceData(myProject);

        projectRootNode.add(moduleNode);
        projectRootNode.add(moduleNode2);
        TreeModel treeModel = new DefaultTreeModel(projectRootNode);
        myTree.setModel(treeModel);
    }

    private DefaultMutableTreeNode convert(RestServiceModule restServiceModule, ProjectNode projectNode) {
        List<RestServiceItem> restServiceItems = restServiceModule.getServiceItems();
        if (restServiceItems == null || restServiceItems.size() == 0) {
            return null;
        }

        ModuleNode moduleNode = new ModuleNode(projectNode, restServiceModule);
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


