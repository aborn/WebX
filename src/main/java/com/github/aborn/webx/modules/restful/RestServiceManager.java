package com.github.aborn.webx.modules.restful;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.github.aborn.webx.datatypes.RestServiceModule;
import com.github.aborn.webdevtoolkit.zhaow.RestServiceDataManager;
import com.github.aborn.webdevtoolkit.zhaow.RestServiceProject;
import com.intellij.openapi.project.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO remove zhaow
 * @author aborn
 * @date 2021/02/01 8:06 PM
 */
public class RestServiceManager {

    public static List<RestServiceModule> buildRestServiceData(Project project) {
        List<RestServiceModule> restServiceModules = new ArrayList<>();
        List<RestServiceProject> restServiceProjects = RestServiceDataManager.buildRestServiceData(project);
        for (RestServiceProject p : restServiceProjects) {
            restServiceModules.add(convert(p));
        }

        return restServiceModules;
    }

    private static RestServiceModule convert(RestServiceProject p) {
        List<RestServiceItem> restServiceItems = new ArrayList<>();
        for (com.github.aborn.webdevtoolkit.zhaow.RestServiceItem restServiceItem : p.getServiceItems()) {
            RestServiceItem item = new RestServiceItem(restServiceItem.getPsiElement(), restServiceItem.getRequestMethod(), restServiceItem.getUrl());
            restServiceItems.add(item);
        }
        return new RestServiceModule(p.getModuleName(), restServiceItems);

    }

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
}
