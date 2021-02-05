package com.github.aborn.webx.modules.restful.resolvers;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author aborn
 * @date 2021/02/05 11:41 AM
 */
public class JavaxrsAnnotationHelper implements AnnotationHelper {

    @Override
    public String getClassUriPath(PsiClass psiClass) {
        PsiAnnotation annotation = psiClass.getModifierList().findAnnotation(JaxrsPathAnnotation.PATH.getQualifiedName());
        String path = PsiAnnotationHelper.getAnnotationAttributeValue(annotation, "value");
        return path != null ? path : "";
    }

    @Override
    public RequestPath[] getRequestPaths(PsiMethod psiMethod) {
        PsiAnnotation[] annotations = psiMethod.getModifierList().getAnnotations();
        if (annotations == null) {
            return null;
        }

        List<RequestPath> list = new ArrayList<>();

        PsiAnnotation wsPathAnnotation = psiMethod.getModifierList().findAnnotation(JaxrsPathAnnotation.PATH.getQualifiedName());
        String path = wsPathAnnotation == null ? psiMethod.getName() : getWsPathValue(wsPathAnnotation);
        JaxrsHttpMethodAnnotation[] jaxrsHttpMethodAnnotations = JaxrsHttpMethodAnnotation.values();

        Arrays.stream(annotations).forEach(a -> Arrays.stream(jaxrsHttpMethodAnnotations).forEach(methodAnnotation -> {
            if (a.getQualifiedName().equals(methodAnnotation.getQualifiedName())) {
                list.add(new RequestPath(path, methodAnnotation.getShortName()));
            }
        }));

        return list.toArray(new RequestPath[list.size()]);
    }

    private String getWsPathValue(PsiAnnotation annotation) {
        String value = PsiAnnotationHelper.getAnnotationAttributeValue(annotation, "value");

        return value != null ? value : "";
    }
}
