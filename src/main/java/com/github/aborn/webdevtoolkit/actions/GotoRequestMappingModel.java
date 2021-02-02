package com.github.aborn.webdevtoolkit.actions;

import com.github.aborn.webdevtoolkit.datatypes.RestServiceItem;
import com.github.aborn.webdevtoolkit.datatypes.enums.HttpMethod;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.gotoByName.CustomMatcherModel;
import com.intellij.ide.util.gotoByName.FilteringGotoByModel;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.codeStyle.MinusculeMatcher;
import com.intellij.psi.codeStyle.NameUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author aborn
 * @date 2021/02/02 7:39 PM
 */
public class GotoRequestMappingModel extends FilteringGotoByModel<HttpMethod> implements DumbAware, CustomMatcherModel {

    protected GotoRequestMappingModel(@NotNull Project project, @NotNull ChooseByNameContributor[] contributors) {
        super(project, contributors);
    }

    @Nullable
    @Override
    protected HttpMethod filterValueFor(NavigationItem item) {
        if (item instanceof RestServiceItem) {
            return ((RestServiceItem) item).getHttpMethod();
        }

        return null;
    }

    @Override
    public String getPromptText() {
        return "Enter service URL path :";
    }

    @Override
    public String getNotInMessage() {
        return "No matched method found";
    }

    @Override
    public String getNotFoundMessage() {
        return "Service path not found";
    }

    @Override
    public boolean loadInitialCheckBoxState() {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(myProject);
        return propertiesComponent.isTrueValue("GoToRestService.OnlyCurrentModule");
    }

    @Override
    public void saveInitialCheckBoxState(boolean state) {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(myProject);
        if (propertiesComponent.isTrueValue("GoToRestService.OnlyCurrentModule")) {
            propertiesComponent.setValue("GoToRestService.OnlyCurrentModule", Boolean.toString(state));
        }
    }

    @Nullable
    @Override
    public String getFullName(Object element) {
        return getElementName(element);
    }

    @NotNull
    @Override
    public String[] getSeparators() {
        return new String[]{"/", "?"};
    }


    /**
     * return null to hide checkbox panel
     */
    @Nullable
    @Override
    public String getCheckBoxName() {
        return "Only This Module";
    }


    @Override
    public boolean willOpenEditor() {
        return true;
    }

    @Override
    public boolean matches(@NotNull String popupItem, @NotNull String userPattern) {
        String pattern = userPattern;
        if (pattern.equals("/")) return true;
        MinusculeMatcher matcher = NameUtil.buildMatcher("*" + pattern, NameUtil.MatchingCaseSensitivity.NONE);

        return matcher.matches(popupItem);
    }
}
