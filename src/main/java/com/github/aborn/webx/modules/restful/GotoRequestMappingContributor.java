package com.github.aborn.webx.modules.restful;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.github.aborn.webx.modules.restful.data.RestServiceManager;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author aborn
 * @date 2021/02/02 8:10 PM
 */
public class GotoRequestMappingContributor implements ChooseByNameContributor {

    Module myModule;

    private List<RestServiceItem> navItem;

    public GotoRequestMappingContributor(Module myModule) {
        this.myModule = myModule;
    }

    @NotNull
    @Override
    public String[] getNames(Project project, boolean onlyThisModuleChecked) {
        List<RestServiceItem> itemList = RestServiceManager.buildRestServiceItemListFrom(project);
        this.navItem = itemList;
        String[] names = new String[itemList.size()];

        for (int i = 0; i < itemList.size(); i++) {
            RestServiceItem requestMappingNavigationItem = itemList.get(i);
            names[i] = requestMappingNavigationItem.getName();
        }

        return names;
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean onlyThisModuleChecked) {
        NavigationItem[] navigationItems = navItem.stream().filter(item -> item.getName().equals(name)).toArray(NavigationItem[]::new);
        return navigationItems;

    }
}
