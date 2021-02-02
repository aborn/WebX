package com.github.aborn.webdevtoolkit.datatypes;

import com.github.aborn.webdevtoolkit.datatypes.enums.HttpMethod;
import com.github.aborn.webdevtoolkit.utils.IconUtils;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

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
        return new RestServiceItemPresentation();
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

    private class RestServiceItemPresentation implements ItemPresentation {
        @Nullable
        @Override
        public String getPresentableText() {
            return uri;
        }

        // 对应的文件位置显示
        @Nullable
        @Override
        public String getLocationString() {
            String fileName = psiElement.getContainingFile().getName();
            String location = null;
            if (psiElement instanceof PsiMethod) {
                PsiMethod psiMethod = ((PsiMethod) psiElement);;
                location = psiMethod.getContainingClass().getName().concat("#").concat(psiMethod.getName());
            }
            return "(" + location + ")";
        }

        @Nullable
        @Override
        public Icon getIcon(boolean unused) {
            return IconUtils.METHOD.get(httpMethod);
        }
    }
}
