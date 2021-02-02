package com.github.aborn.webdevtoolkit.datatypes;

import com.github.aborn.webdevtoolkit.datatypes.enums.HttpMethod;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.Nullable;

/**
 * @author aborn
 * @date 2021/02/01 3:05 PM
 */
public class RestServiceItem implements NavigationItem {
    private String uri;
    private Navigatable navigationElement;
    private PsiMethod psiMethod;
    private PsiElement psiElement;
    private HttpMethod httpMethod;

    public RestServiceItem(String uri) {
        this.uri = uri;
    }

    public RestServiceItem(PsiElement psiElement, String requestMethod, String uri) {
        this.psiElement = psiElement;
        if (psiElement instanceof PsiMethod) {
            this.psiMethod = (PsiMethod) psiElement;
        }

        this.uri = uri;
        this.httpMethod = HttpMethod.getMethod(requestMethod);
        if (psiElement instanceof Navigatable) {
            navigationElement = (Navigatable) psiElement;
        }
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
        if (navigationElement != null) {
            navigationElement.navigate(requestFocus);
        }
    }

    @Override
    public boolean canNavigate() {
        return navigationElement != null && navigationElement.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }

    public PsiElement getPsiElement() {
        return psiElement;
    }

    public PsiMethod getPsiMethod() {
        return psiMethod;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
