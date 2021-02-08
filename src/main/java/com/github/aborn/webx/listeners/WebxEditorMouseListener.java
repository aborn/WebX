package com.github.aborn.webx.listeners;

import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author aborn
 * @date 2021/02/08 4:09 PM
 */
public class WebxEditorMouseListener extends UserActionBaseListener implements EditorMouseListener {

    @Override
    public void mousePressed(EditorMouseEvent editorMouseEvent) {
        FileDocumentManager instance = FileDocumentManager.getInstance();
        VirtualFile file = instance.getFile(editorMouseEvent.getEditor().getDocument());
        Project project = editorMouseEvent.getEditor().getProject();
        info("mouse pressed. projectName:" + project.getName());
    }

}
