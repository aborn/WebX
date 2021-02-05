package com.github.aborn.webx.modules.restful.resolvers;

/**
 * @author aborn
 * @date 2021/02/05 11:21 AM
 */
public enum SpringControllerAnnotation implements AnnotationPath {
    // Controller
    CONTROLLER("Controller", "org.springframework.stereotype.Controller"),

    // RestController
    REST_CONTROLLER("RestController", "org.springframework.web.bind.annotation.RestController");

    SpringControllerAnnotation(String annotationName, String qualifiedName) {
        this.annotationName = annotationName;
        this.qualifiedName = qualifiedName;
    }

    private String annotationName;
    private String qualifiedName;

    @Override
    public String getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public String getShortName() {
        return annotationName;
    }
}
