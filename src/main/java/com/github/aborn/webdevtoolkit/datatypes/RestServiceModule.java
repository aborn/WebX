package com.github.aborn.webdevtoolkit.datatypes;

import com.intellij.openapi.module.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aborn
 * @date 2021/02/01 3:14 PM
 */
public class RestServiceModule {
    String appName;
    String moduleName;
    Module module;

    List<RestServiceItem> serviceItems = new ArrayList<>();

    public RestServiceModule(String moduleName, List<RestServiceItem> serviceItems) {
        this.moduleName = moduleName;
        appName = moduleName;
        this.serviceItems = serviceItems;
    }

    public RestServiceModule(Module module, List<RestServiceItem> serviceItems) {
        this.moduleName = module.getName();
        appName = moduleName;
        this.serviceItems = serviceItems;
    }

    public String getModuleName() {
        return moduleName;
    }

    public List<RestServiceItem> getServiceItems() {
        return serviceItems;
    }
}
