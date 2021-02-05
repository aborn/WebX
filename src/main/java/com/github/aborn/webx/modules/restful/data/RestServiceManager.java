package com.github.aborn.webx.modules.restful.data;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.github.aborn.webx.datatypes.RestServiceModule;
import com.github.aborn.webx.modules.restful.resolvers.IResolver;
import com.github.aborn.webx.modules.restful.resolvers.JavaxrsResolver;
import com.github.aborn.webx.modules.restful.resolvers.SpringResolver;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * @author aborn
 * @date 2021/02/01 8:06 PM
 */
public class RestServiceManager {

    public static final Logger LOG = Logger.getInstance(RestServiceManager.class);

    public static List<RestServiceModule> buildRestServiceData(Project project) {
        List<RestServiceModule> serviceProjectList = Lists.newArrayList();
        Module[] modules = ModuleManager.getInstance(project).getModules();

        if (modules.length > 0) {
            for (Module module : modules) {
                List<RestServiceItem> restServices = buildRestServiceItemListFrom(project, module);
                if (restServices.size() > 0) {
                    serviceProjectList.add(new RestServiceModule(module.getName() + "(" + restServices.size() +")", restServices));
                }
            }
            LOG.info("Find " + modules.length + " modules in " + project.getName() + " project.");
        } else {
            List<RestServiceItem> restServices = buildRestServiceItemListFrom(project);
            if (restServices.size() > 0) {
                serviceProjectList.add(new RestServiceModule(project.getName() + "(" + restServices.size() +")", restServices));
            }
            LOG.info("Not find any modules in project " + project.getName() + ".");
        }

        return serviceProjectList;
    }

    /**
     * from module
     * @param project
     * @param module
     * @return
     */
    private static List<RestServiceItem> buildRestServiceItemListFrom(Project project, Module module) {
        IResolver[] resolvers = {new SpringResolver(), new JavaxrsResolver()};
        List<RestServiceItem> restServices = Lists.newArrayList();
        for (IResolver resolver : resolvers) {
            restServices.addAll(resolver.getRestServiceItemList(project, module));
        }
        return restServices;
    }

    /**
     * from project
     * @param project
     * @return
     */
    public static List<RestServiceItem> buildRestServiceItemListFrom(Project project) {
        IResolver[] resolvers = {new SpringResolver(), new JavaxrsResolver()};
        List<RestServiceItem> restServices = Lists.newArrayList();
        for (IResolver resolver : resolvers) {
            restServices.addAll(resolver.getRestServiceItemList(project));
        }
        return restServices;
    }

    /**
    public static List<RestServiceItem> buildRestServiceItemListFrom(Project project) {
        List<RestServiceItem> restServiceItems = new ArrayList<>();

        List<RestServiceProject> restServiceProjects = RestServiceDataManager.buildRestServiceData(project);
        for (RestServiceProject p : restServiceProjects) {
            for (com.github.aborn.webdevtoolkit.zhaow.RestServiceItem restServiceItem : p.getServiceItems()) {
                RestServiceItem item = new RestServiceItem(restServiceItem.getPsiElement(), restServiceItem.getRequestMethod(), restServiceItem.getUrl());
                restServiceItems.add(item);
            }
        }

        return restServiceItems;
    }
     */
}
