package com.github.aborn.webx.listeners;

import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author aborn
 * @date 2021/02/08 2:26 PM
 */
public class WebxVfsListener extends UserActionBaseListener implements BulkFileListener {

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        VFileEvent vFileEvent = events.get(0);
        info("vfs event. project: " );
    }
}
