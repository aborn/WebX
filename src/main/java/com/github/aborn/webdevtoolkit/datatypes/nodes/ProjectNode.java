package com.github.aborn.webdevtoolkit.datatypes.nodes;

import com.github.aborn.webdevtoolkit.utils.IconUtils;
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
        getTemplatePresentation().setIcon(IconUtils.MODULE_GROUP);
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
