package com.github.aborn.webx.modules.restful.resolvers;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author aborn
 * @date 2021/02/05 11:03 AM
 */
public abstract class BaseResolver implements IResolver {

    protected AnnotationHelper annotationHelper;

    @NotNull
    protected RestServiceItem buildRestServiceItem(PsiElement psiMethod, String classUriPath, RequestPath requestMapping) {
        if (!classUriPath.startsWith("/")) {
            classUriPath = "/".concat(classUriPath);
        }
        if (!classUriPath.endsWith("/")) {
            classUriPath = classUriPath.concat("/");
        }

        String methodPath = requestMapping.getPath();

        if (methodPath.startsWith("/")) {
            methodPath = methodPath.substring(1);
        }
        String requestPath = classUriPath + methodPath;
        return new RestServiceItem(psiMethod, requestMapping.getMethod(), requestPath);
    }
}
