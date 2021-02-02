package com.github.aborn.webdevtoolkit.utils;

import com.github.aborn.webdevtoolkit.datatypes.enums.HttpMethod;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author aborn
 * @date 2021/02/01 6:54 PM
 */
public class IconUtils {

    // 16x16
    public static final Icon MODULE = AllIcons.Nodes.ModuleGroup;
    public static final Icon MODULE_GROUP = AllIcons.Nodes.ModuleGroup;

    public static class METHOD {
        public static Icon get(HttpMethod method) {
            if (method == null) {
                return UNDEFINED;
            }

            if (method.equals(HttpMethod.GET)) {
                return METHOD.GET;
            } else if(method.equals(HttpMethod.POST)) {
                return METHOD.POST;
            } else if (method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)) {
                return METHOD.PUT;
            } else if(method.equals(HttpMethod.DELETE)) {
                return METHOD.DELETE;
            }

            return UNDEFINED;
        }

        public static  Icon GET = IconLoader.getIcon("/icons/method/g.png"); // 16x16 GREEN
        public static  Icon PUT = IconLoader.getIcon("/icons/method/p2.png"); // 16x16 ORANGE
        public static  Icon POST = IconLoader.getIcon("/icons/method/p.png"); // 16x16 BLUE
        public static  Icon PATCH = IconLoader.getIcon("/icons/method/p3.png"); // 16x16 GRAY
        public static  Icon DELETE = IconLoader.getIcon("/icons/method/d.png"); // 16x16 RED
        public static  Icon UNDEFINED = IconLoader.getIcon("/icons/method/undefined.png"); // 16x16 GRAY
    }
}
