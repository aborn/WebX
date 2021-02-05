package com.github.aborn.webx.modules.restful.resolvers;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;

import java.util.List;

/**
 * @author aborn
 * @date 2021/02/05 11:01 AM
 */
public interface IResolver {

    /**
     * build data from module in project
     * @param project
     * @param module
     * @return
     */
    List<RestServiceItem> getRestServiceItemList(Project project, Module module);

    /**
     * build data from all project
     * @param project
     * @return
     */
    List<RestServiceItem> getRestServiceItemList(Project project);
}
