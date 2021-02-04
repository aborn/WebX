package com.github.aborn.webx.modules.restful;

import com.github.aborn.webx.modules.restful.utils.ToolkitUtil;
import com.intellij.ide.util.gotoByName.ChooseByNameBase;
import com.intellij.ide.util.gotoByName.DefaultChooseByNameItemProvider;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.psi.PsiElement;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author aborn
 * @date 2021/02/04 3:45 PM
 */
public class GotoRequestMappingProvider extends DefaultChooseByNameItemProvider {

    public GotoRequestMappingProvider(@Nullable PsiElement context) {
        super(context);
    }

    @NotNull
    @Override
    public List<String> filterNames(@NotNull ChooseByNameBase base, @NotNull String[] names, @NotNull String pattern) {
        return super.filterNames(base, names, pattern);
    }

    @Override
    public boolean filterElements(@NotNull ChooseByNameBase base, @NotNull String pattern, boolean everywhere,
                                  @NotNull ProgressIndicator indicator, @NotNull Processor<Object> consumer) {
        return super.filterElements(base, ToolkitUtil.removeRedundancyMarkup(pattern), everywhere, indicator, consumer);
    }
}
