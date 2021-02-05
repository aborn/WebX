package com.github.aborn.webx.modules.restful.resolvers;

/**
 * @author aborn
 * @date 2021/02/05 11:43 AM
 */
public enum JaxrsPathAnnotation implements AnnotationPath {

    // path
    PATH("Path", "javax.ws.rs.Path");

    JaxrsPathAnnotation(String shortName, String qualifiedName) {
        this.shortName = shortName;
        this.qualifiedName = qualifiedName;
    }

    private String shortName;
    private String qualifiedName;

    @Override
    public String getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public String getShortName() {
        return shortName;
    }
}
