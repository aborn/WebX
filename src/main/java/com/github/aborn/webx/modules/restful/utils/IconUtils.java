package com.github.aborn.webx.modules.restful.utils;

import com.github.aborn.webx.datatypes.enums.HttpMethod;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author aborn
 * @date 2021/02/01 6:54 PM
 */
public class IconUtils {

    /**
     * MODULE
     */
    public static final Icon MODULE = AllIcons.Nodes.ModuleGroup;
    public static final Icon MODULE_GROUP = AllIcons.Nodes.ModuleGroup;

    public static class METHOD {
        public static Icon get(HttpMethod method) {
            if (method == null) {
                return METHOD.WEBX;
            }

            switch (method) {
                case GET:
                    return METHOD.GET;
                case POST:
                    return METHOD.POST;
                case PUT:
                case PATCH:
                    return METHOD.PUT;
                case DELETE:
                    return METHOD.DELETE;
                default:
                    return METHOD.WEBX;
            }
        }

        public static Icon GET = IconLoader.getIcon("/icons/method/g.png");    // 16x16 GREEN
        public static Icon PUT = IconLoader.getIcon("/icons/method/p2.png");   // 16x16 ORANGE
        public static Icon POST = IconLoader.getIcon("/icons/method/p.png");   // 16x16 BLUE
        public static Icon PATCH = IconLoader.getIcon("/icons/method/p3.png"); // 16x16 GRAY
        public static Icon DELETE = IconLoader.getIcon("/icons/method/d.png"); // 16x16 RED
        public static Icon UNDEFINED = IconLoader.getIcon("/icons/method/undefined.png"); // 16x16 GRAY
        public static Icon WEBX = IconLoader.getIcon("/icons/method/webx.png"); // 16x16
    }
}
