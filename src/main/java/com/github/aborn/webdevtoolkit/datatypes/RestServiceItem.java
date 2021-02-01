package com.github.aborn.webdevtoolkit.datatypes;

import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import org.jetbrains.annotations.Nullable;

/**
 * @author aborn
 * @date 2021/02/01 3:05 PM
 */
public class RestServiceItem implements NavigationItem {
    String uri;

    public RestServiceItem(String uri) {
        this.uri = uri;
    }

    @Override
    public @Nullable String getName() {
        return uri;
    }

    @Override
    public @Nullable ItemPresentation getPresentation() {
        return null;
    }

    @Override
    public void navigate(boolean requestFocus) {

    }

    @Override
    public boolean canNavigate() {
        return false;
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }
}
