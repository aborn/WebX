package com.github.aborn.webx.modules.restful.resolvers;

import com.github.aborn.webx.datatypes.RestServiceItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author aborn
 * @date 2021/02/05 11:05 AM
 */
public class SpringResolver extends BaseResolver {

    public SpringResolver() {
        annotationHelper = new SpringAnnotationHelper();
    }

    @Override
    public List<RestServiceItem> getRestServiceItemList(Project project) {
        Collection<PsiAnnotation> psiAnnotations = new ArrayList<>();
        for (SpringControllerAnnotation annotation : SpringControllerAnnotation.values()) {
            psiAnnotations.addAll(JavaAnnotationIndex.getInstance().get(annotation.getShortName(), project, GlobalSearchScope.projectScope(project)));
        }
        return build(psiAnnotations, null);
    }

    @Override
    public List<RestServiceItem> getRestServiceItemList(Project project, Module module) {
        Collection<PsiAnnotation> psiAnnotations = new ArrayList<>();
        for (SpringControllerAnnotation annotation : SpringControllerAnnotation.values()) {
            psiAnnotations.addAll(JavaAnnotationIndex.getInstance().get(annotation.getShortName(), project, GlobalSearchScope.moduleScope(module)));
        }
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

            if (psiMethods == null) {
                return;
            }

            List<RestServiceItem> restServiceItems = getServiceItemList(psiClass, module);

            itemList.addAll(restServiceItems);

        });

        return itemList;
    }

    protected List<RestServiceItem> getServiceItemList(PsiClass psiClass, Module module) {

        PsiMethod[] psiMethods = psiClass.getMethods();
        if (psiMethods == null) {
            return new ArrayList<>();
        }

        List<RestServiceItem> itemList = new ArrayList<>();
        List<RequestPath> classRequestPaths = RequestMappingAnnotationHelper.getRequestPaths(psiClass);

        for (PsiMethod psiMethod : psiMethods) {
            RequestPath[] methodRequestPaths = annotationHelper.getRequestPaths(psiMethod);

            for (RequestPath classRequestPath : classRequestPaths) {
                for (RequestPath methodRequestPath : methodRequestPaths) {
                    String path = classRequestPath.getPath();

                    RestServiceItem item = buildRestServiceItem(psiMethod, path, methodRequestPath);
                    if (module != null) {
                        item.setModule(module);
                    }
                    itemList.add(item);
                }
            }

        }
        return itemList;
    }
}
