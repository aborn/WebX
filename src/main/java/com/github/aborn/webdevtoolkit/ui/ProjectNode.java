package com.github.aborn.webdevtoolkit.ui;

import com.github.aborn.webdevtoolkit.ui.BaseNode;
import com.github.aborn.webdevtoolkit.ui.ModuleNode;
import com.intellij.ui.treeStructure.SimpleNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aborn
 * @date 2021/02/01 2:51 PM
 */
public class ProjectNode extends BaseNode {
    List<ModuleNode> moduleNodes = new ArrayList<>();

    public ProjectNode() {
        super(null);
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return moduleNodes.toArray(new SimpleNode[moduleNodes.size()]);
    }


    private int serviceCount = 0;

    @Override
    public String getName() {
        String s = "Found %d services ";
        return serviceCount > 0 ? String.format(s, serviceCount) : null;
    }

    public void updateServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }
}
