package com.github.aborn.webx.modules.restful.resolvers;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aborn
 * @date 2021/02/05 11:30 AM
 */
public class SpringAnnotationHelper implements AnnotationHelper {

    @Override
    public String getClassUriPath(PsiClass psiClass) {
        return null;
    }

    @Override
    public RequestPath[] getRequestPaths(PsiMethod psiMethod) {
        PsiAnnotation[] annotations = psiMethod.getModifierList().getAnnotations();

        if (annotations == null) {
            return null;
        }
        List<RequestPath> list = new ArrayList<>();

        for (PsiAnnotation annotation : annotations) {
            for (SpringRequestMethodAnnotation mappingAnnotation : SpringRequestMethodAnnotation.values()) {
                if (annotation.getQualifiedName() !=null &&
                        mappingAnnotation.getQualifiedName().endsWith(annotation.getQualifiedName())) {
                    String defaultValue = "/";
                    List<RequestPath> requestMappings = getRequestMappings(annotation, defaultValue, mappingAnnotation);
                    if (requestMappings.size() > 0) {
                        list.addAll(requestMappings);
                    }
                }
            }
        }

        return list.toArray(new RequestPath[list.size()]);
    }

    private static List<RequestPath> getRequestMappings(PsiAnnotation annotation, String defaultValue, SpringRequestMethodAnnotation mappingAnnotation) {
        List<RequestPath> mappingList = new ArrayList<>();

        List<String> methodList = PsiAnnotationHelper.getAnnotationAttributeValues(annotation, "method");

        List<String> pathList = PsiAnnotationHelper.getAnnotationAttributeValues(annotation, "value");
        if (pathList.size() == 0) {
            pathList = PsiAnnotationHelper.getAnnotationAttributeValues(annotation, "path");
        }

        // 没有设置 value，默认方法名
        if (pathList.size() == 0) {
            pathList.add(defaultValue);
        }

        String defaultMethodType = mappingAnnotation.methodName();
        if (methodList.size() > 0) {
            for (String method : methodList) {
                for (String path : pathList) {
                    mappingList.add(new RequestPath(path, method));
                }
            }
        } else {
            for (String path : pathList) {
                mappingList.add(new RequestPath(path, defaultMethodType));
            }
        }

        return mappingList;
    }
}
