package com.github.aborn.webx.datatypes.nodes;

import com.github.aborn.webx.modules.restful.utils.IconUtils;
import com.intellij.ui.treeStructure.SimpleNode;
import org.apache.commons.collections.CollectionUtils;

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
        return serviceCount > 0 ? String.format(s, serviceCount) : "No services found.";
    }

    public void updateServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public void updateModuleNodes(List<ModuleNode> moduleNodes) {
        if (CollectionUtils.isEmpty(moduleNodes)) {
            this.serviceCount = 0;
            return;
        }
        this.moduleNodes = moduleNodes;

        int count = 0;
        for (ModuleNode moduleNode : moduleNodes) {
            count += moduleNode.getServiceCount();
        }
        this.serviceCount = count;
    }

    public void addModuleNode(ModuleNode moduleNode) {
        moduleNodes.add(moduleNode);
        this.serviceCount += moduleNode.getServiceCount();
    }
}
