package com.github.aborn.webdevtoolkit.ui;

import com.github.aborn.webdevtoolkit.datatypes.RestServiceItem;
import com.github.aborn.webdevtoolkit.ui.BaseNode;
import com.intellij.ui.treeStructure.SimpleNode;

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
}
