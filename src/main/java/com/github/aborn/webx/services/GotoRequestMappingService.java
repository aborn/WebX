package com.github.aborn.webx.services;

import com.github.aborn.webx.datatypes.enums.HttpMethod;
import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;

/**
 * @author aborn
 * @date 2021/02/02 7:35 PM
 */
@State(name = "GotoRequestMappingService", storages = @Storage(StoragePathMacros.WORKSPACE_FILE))
public class GotoRequestMappingService extends ChooseByNameFilterConfiguration<HttpMethod> {

    public static GotoRequestMappingService getInstance(Project project) {
        return ServiceManager.getService(project, GotoRequestMappingService.class);
    }

    @Override
    protected String nameForElement(HttpMethod type) {
        return type.name();
    }
}
