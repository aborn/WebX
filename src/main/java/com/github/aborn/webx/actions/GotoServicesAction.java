package com.github.aborn.webx.actions;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.github.aborn.webx.datatypes.enums.HttpMethod;
import com.github.aborn.webx.modules.restful.GotoRequestMappingContributor;
import com.github.aborn.webx.modules.restful.GotoRequestMappingModel;
import com.github.aborn.webx.modules.restful.GotoRequestMappingProvider;
import com.github.aborn.webx.modules.restful.RSChooseByNamePopup;
import com.github.aborn.webx.services.GotoRequestMappingService;
import com.intellij.featureStatistics.FeatureUsageTracker;
import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNameItemProvider;
import com.intellij.ide.util.gotoByName.ChooseByNameModel;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.util.Arrays;
import java.util.List;

/**
 * @author aborn
 * @date 2021/02/01 9:27 PM
 */
public class GotoServicesAction extends GotoActionBase implements DumbAware {

    @Override
    protected void gotoActionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }
        FeatureUsageTracker.getInstance().triggerFeatureUsed("navigation.popup.service");

        ChooseByNameContributor[] chooseByNameContributors = {
                new GotoRequestMappingContributor(e.getData(LangDataKeys.MODULE))
        };

        final GotoRequestMappingModel model = new GotoRequestMappingModel(project, chooseByNameContributors);

        GotoActionCallback<HttpMethod> callback = new GotoActionCallback<HttpMethod>() {

            @Override
            protected ChooseByNameFilter<HttpMethod> createFilter(@NotNull ChooseByNamePopup popup) {
                return new GotoRequestMappingFilter(popup, model, project);
            }

            @Override
            public void elementChosen(ChooseByNamePopup chooseByNamePopup, Object element) {
                if (element instanceof RestServiceItem) {
                    RestServiceItem navigationItem = (RestServiceItem) element;
                    if (navigationItem.canNavigate()) {
                        navigationItem.navigate(true);
                    }
                }
            }
        };

        GotoRequestMappingProvider provider = new GotoRequestMappingProvider(getPsiContext(e));
        showNavigationPopup(e, project, model, callback, "Request Mapping Url matching pattern",
                true, true, (ChooseByNameItemProvider) provider);
    }

    protected <T> void showNavigationPopup(AnActionEvent e,
                                           @NotNull Project project,
                                           ChooseByNameModel model,
                                           final GotoActionCallback<T> callback,
                                           @Nullable final String findUsagesTitle,
                                           boolean useSelectionFromEditor,
                                           final boolean allowMultipleSelection,
                                           final ChooseByNameItemProvider itemProvider) {

        boolean mayRequestOpenInCurrentWindow = model.willOpenEditor() && FileEditorManagerEx.getInstanceEx(project).hasSplitOrUndockedWindows();
        Pair<String, Integer> start = getInitialText(useSelectionFromEditor, e);
        String copiedUrl = tryFindCopiedUrl();
        String predefinedText = start.first == null ? copiedUrl : start.first;

        showNavigationPopup(callback, findUsagesTitle,
                RSChooseByNamePopup.createPopup(project, model, itemProvider, predefinedText,
                        mayRequestOpenInCurrentWindow,
                        start.second), allowMultipleSelection);
    }

    private String tryFindCopiedUrl() {
        String contents = CopyPasteManager.getInstance().getContents(DataFlavor.stringFlavor);
        if (contents == null) {
            return null;
        }

        contents = contents.trim();
        if (contents.startsWith("http")) {
            if (contents.length() <= 120) {
                return contents;
            } else {
                return contents.substring(0, 120);
            }
        }

        return null;
    }

    protected static class GotoRequestMappingFilter extends ChooseByNameFilter<HttpMethod> {
        GotoRequestMappingFilter(final ChooseByNamePopup popup, GotoRequestMappingModel model, final Project project) {
            super(popup, model, GotoRequestMappingService.getInstance(project), project);
        }

        @Override
        @NotNull
        protected List<HttpMethod> getAllFilterValues() {
            return Arrays.asList(HttpMethod.values());
        }

        @Override
        protected String textForFilterValue(@NotNull HttpMethod value) {
            return value.name();
        }

        @Override
        protected Icon iconForFilterValue(@NotNull HttpMethod value) {
            return null;
        }
    }
}
