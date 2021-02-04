package com.github.aborn.webx.datatypes.nodes;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.github.aborn.webx.datatypes.RestServiceModule;
import com.github.aborn.webx.modules.restful.utils.IconUtils;
import com.intellij.ui.treeStructure.SimpleNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 最顶层 RootNode，每个Project唯一一个
 * @author aborn
 * @date 2021/02/01 2:49 PM
 */
public class ModuleNode extends BaseNode {
    List<ServiceNode> serviceNodes = new ArrayList<>();
    RestServiceModule restServiceModule;

    public ModuleNode(SimpleNode parent, RestServiceModule restServiceModule) {
        super(parent);
        this.restServiceModule = restServiceModule;
        for (RestServiceItem restServiceItem : restServiceModule.getServiceItems()) {
            this.serviceNodes.add(new ServiceNode(this, restServiceItem));
        }

        getTemplatePresentation().setIcon(IconUtils.MODULE);
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return serviceNodes.toArray(new SimpleNode[serviceNodes.size()]);
    }

    @Override
    public String getName() {
        return restServiceModule.getModuleName();
    }

    public int getServiceCount() {
        return serviceNodes.size();
    }
}
