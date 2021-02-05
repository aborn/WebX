package com.github.aborn.webx.modules.restful.resolvers;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.compress.utils.Lists;

import java.util.Collection;
import java.util.List;

/**
 * @author aborn
 * @date 2021/02/05 11:06 AM
 */
public class JavaxrsResolver extends BaseResolver {

    public JavaxrsResolver() {
        annotationHelper = new JavaxrsAnnotationHelper();
    }

    @Override
    public List<RestServiceItem> getRestServiceItemList(Project project) {
        Collection<PsiAnnotation> psiAnnotations = JavaAnnotationIndex.getInstance().get(JaxrsPathAnnotation.PATH.getShortName(), project, GlobalSearchScope.projectScope(project));
        return build(psiAnnotations, null);
    }

    @Override
    public List<RestServiceItem> getRestServiceItemList(Project project, Module module) {
        Collection<PsiAnnotation> psiAnnotations = JavaAnnotationIndex.getInstance().get(JaxrsPathAnnotation.PATH.getShortName(), project, GlobalSearchScope.moduleScope(module));
        return build(psiAnnotations, module);
    }

    private List<RestServiceItem> build(Collection<PsiAnnotation> psiAnnotations, Module module) {
        List<RestServiceItem> itemList = Lists.newArrayList();
        psiAnnotations.forEach(psiAnnotation -> {
            PsiModifierList psiModifierList = (PsiModifierList) psiAnnotation.getParent();
            PsiElement psiElement = psiModifierList.getParent();

            if (!(psiElement instanceof PsiClass)) {
                return;
            }

            PsiClass psiClass = (PsiClass) psiElement;
            PsiMethod[] psiMethods = psiClass.getMethods();

            String classUriPath = annotationHelper.getClassUriPath(psiClass);
            for (PsiMethod psiMethod : psiMethods) {
                RequestPath[] methodUriPaths = annotationHelper.getRequestPaths(psiMethod);

                for (RequestPath methodUriPath : methodUriPaths) {
                    RestServiceItem item = buildRestServiceItem(psiMethod, classUriPath, methodUriPath);
                    if (module != null) {
                        item.setModule(module);
                    }
                    itemList.add(item);
                }
            }

        });

        return itemList;
    }
}
