package com.github.aborn.webx.datatypes.nodes;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;

/**
 * @author aborn
 * @date 2021/02/01 2:53 PM
 */
public class BaseNode extends CachingSimpleNode {

    protected BaseNode(SimpleNode aParent) {
        super(aParent);
    }

    @Override
    protected SimpleNode[] buildChildren() {
        return new SimpleNode[0];
    }

}
