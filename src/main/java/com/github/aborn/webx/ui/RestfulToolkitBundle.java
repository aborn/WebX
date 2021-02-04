package com.github.aborn.webx.ui;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;

/**
 * @author aborn
 * @date 2021/01/29 5:04 PM
 */
public class RestfulToolkitBundle {
    private static Reference<ResourceBundle> ourBundle;

    @NonNls
    private static final String BUNDLE = "RestfulToolkitBundle";

    private RestfulToolkitBundle() {

    }

    public static String message(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        return AbstractBundle.message(getBundle(), key, params);
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = com.intellij.reference.SoftReference.dereference(ourBundle);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE);
            ourBundle = new SoftReference<>(bundle);
        }
        return bundle;
    }
}
