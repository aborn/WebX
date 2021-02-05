package com.github.aborn.webx.modules.restful.resolvers;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aborn
 * @date 2021/02/05 11:35 AM
 */
public class RequestMappingAnnotationHelper {

    /**
     * 过滤所有注解
     *
     * @param psiClass
     * @return
     */
    public static List<RequestPath> getRequestPaths(PsiClass psiClass) {
        PsiAnnotation[] annotations = psiClass.getModifierList().getAnnotations();

        PsiAnnotation requestMappingAnnotation = null;
        List<RequestPath> list = new ArrayList<>();
        for (PsiAnnotation annotation : annotations) {
            for (SpringRequestMethodAnnotation mappingAnnotation : SpringRequestMethodAnnotation.values()) {
                if (annotation.getQualifiedName().equals(mappingAnnotation.getQualifiedName())) {
                    requestMappingAnnotation = annotation;
                }
            }
        }

        if (requestMappingAnnotation != null) {
            List<RequestPath> requestMappings = getRequestMappings(requestMappingAnnotation, "");
            if (requestMappings.size() > 0) {
                list.addAll(requestMappings);
            }
        } else {
            // TODO : 继承 RequestMapping
            PsiClass superClass = psiClass.getSuperClass();
            if (superClass != null && !superClass.getQualifiedName().equals("java.lang.Object")) {
                list = getRequestPaths(superClass);
            } else {
                list.add(new RequestPath("/", null));
            }

        }

        return list;
    }

    /**
     * @param annotation
     * @param defaultValue
     * @return
     */
    private static List<RequestPath> getRequestMappings(PsiAnnotation annotation, String defaultValue) {
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

        // todo: 处理没有设置 value 或 path 的 RequestMapping

//        List<String> finalPathList = pathList;
//        methodList.forEach(method-> finalPathList.forEach(path->mappingList.add(new RequestMapping(path,method))));

        if (methodList.size() > 0) {
            for (String method : methodList) {
                for (String path : pathList) {
                    mappingList.add(new RequestPath(path, method));
                }
            }
        } else {
            for (String path : pathList) {
                mappingList.add(new RequestPath(path, null));
            }
        }

        return mappingList;
    }

    /**
     * 过滤所有注解
     *
     * @param psiMethod
     * @return
     */
    public static RequestPath[] getRequestPaths(PsiMethod psiMethod) {
        PsiAnnotation[] annotations = psiMethod.getModifierList().getAnnotations();

        if (annotations == null) {
            return null;
        }

        List<RequestPath> list = new ArrayList<>();

        for (PsiAnnotation annotation : annotations) {
            for (SpringRequestMethodAnnotation mappingAnnotation : SpringRequestMethodAnnotation.values()) {
//            for (PathMappingAnnotation mappingAnnotation : PathMappingAnnotation.allPathMappingAnnotations) {
                if (mappingAnnotation.getQualifiedName().endsWith(annotation.getQualifiedName())) {

//                    String defaultValue = psiMethod.getName();
                    String defaultValue = "/";
                    List<RequestPath> requestMappings = getRequestMappings(annotation, defaultValue);
                    if (requestMappings.size() > 0) {
                        list.addAll(requestMappings);
                    }
                }
            }
        }

        return list.toArray(new RequestPath[list.size()]);
    }
}
