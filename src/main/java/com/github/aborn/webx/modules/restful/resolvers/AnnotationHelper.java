package com.github.aborn.webx.modules.restful.resolvers;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

/**
 * @author aborn
 * @date 2021/02/05 11:27 AM
 */
public interface AnnotationHelper {
    /**
     * get class uri path
     * @param psiClass
     * @return
     */
    String getClassUriPath(PsiClass psiClass);

    /**
     *
     * @param psiMethod
     * @return
     */
    RequestPath[] getRequestPaths(PsiMethod psiMethod);
}
