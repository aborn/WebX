package com.github.aborn.webdevtoolkit.datatypes.nodes;

import com.github.aborn.webdevtoolkit.datatypes.RestServiceItem;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.ui.treeStructure.SimpleNode;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.util.OpenSourceUtil;

import java.awt.event.InputEvent;

/**
 * @author aborn
 * @date 2021/02/01 2:51 PM
 */
public class ServiceNode extends BaseNode {
    RestServiceItem myServiceItem;

    public ServiceNode(SimpleNode parent, RestServiceItem serviceItem) {
        super(parent);
        myServiceItem = serviceItem;
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return new SimpleNode[0];
    }

    @Override
    public String getName() {
        String name = myServiceItem.getName();
        return name;
    }

    @Override
    public void handleSelection(SimpleTree tree) {
        ServiceNode selectedNode = (ServiceNode) tree.getSelectedNode();
        showServiceDetail(selectedNode.myServiceItem);
    }

    /**
     * 显示Service 详情
     */
    private void showServiceDetail(RestServiceItem serviceItem) {
    }

    /**
     * 双击进入
     * @param tree
     * @param inputEvent
     */
    @Override
    public void handleDoubleClickOrEnter(SimpleTree tree, InputEvent inputEvent) {
        ServiceNode selectedNode = (ServiceNode) tree.getSelectedNode();

        if (selectedNode == null) { return; }
        RestServiceItem myServiceItem = selectedNode.myServiceItem;
        PsiElement psiElement = myServiceItem.getPsiElement();

        // 只支付Java语言
        if (psiElement.getLanguage() == JavaLanguage.INSTANCE) {
            PsiMethod psiMethod = myServiceItem.getPsiMethod();
            OpenSourceUtil.navigate(psiMethod);
        }
    }
}
