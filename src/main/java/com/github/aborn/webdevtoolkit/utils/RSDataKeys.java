package com.github.aborn.webdevtoolkit.utils;

import com.github.aborn.webdevtoolkit.zhaow.RestServiceItem;
import com.intellij.openapi.actionSystem.DataKey;

import java.util.List;

/**
 * @author aborn
 * @date 2021/01/29 5:32 PM
 */
public class RSDataKeys {
    public static final DataKey<List<RestServiceItem>> SERVICE_ITEMS = DataKey.create("SERVICE_ITEMS");

    private RSDataKeys() {
    }
}
