package com.github.aborn.webx.listeners;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * TODO 貌似没有作用？没能收到消息
 * @author aborn
 * @date 2021/02/08 4:11 PM
 */
public class WebxDocumentListener extends UserActionBaseListener implements DocumentListener {
    @Override
    public void documentChanged(DocumentEvent documentEvent) {
        Document document = documentEvent.getDocument();
        FileDocumentManager instance = FileDocumentManager.getInstance();
        VirtualFile file = instance.getFile(document);
        Editor[] editors = EditorFactory.getInstance().getEditors(document);
        if (editors.length > 0) {
            Project project = editors[0].getProject();
            info("document changed. projectName:" + project.getName());
        }
    }
}
