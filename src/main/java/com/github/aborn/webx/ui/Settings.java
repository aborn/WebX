/* ==========================================================
File:        Settings.java
Description: Prompts user for api key if it does not exist.
Maintainer:  WakaTime <support@wakatime.com>
License:     BSD, see LICENSE for more details.
Website:     https://wakatime.com/
===========================================================*/

package com.github.aborn.webx.ui;

import com.github.aborn.webx.datatypes.PlaceholderTextField;
import com.github.aborn.webx.utils.ConfigFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class Settings extends DialogWrapper {
    private final JPanel panel;
    private final JLabel idLabel;
    private final PlaceholderTextField idText;
    private final JLabel tokenLabel;
    private final PlaceholderTextField tokenText;

    public Settings(@Nullable Project project) {
        super(project, true);
        setTitle("WebX User Settings");
        setOKButtonText("Save");
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // ID config
        idLabel = new JLabel("User ID:", JLabel.CENTER);
        panel.add(idLabel);
        idText = new PlaceholderTextField(30);
        idText.setPlaceholder(" Please input your github id.");
        String id = ConfigFile.get("settings", "id");
        idText.setText(id);

        panel.add(idText);

        // Token config
        tokenLabel = new JLabel("User Token:", JLabel.CENTER);
        panel.add(tokenLabel);
        tokenText = new PlaceholderTextField();
        tokenText.setPlaceholder(" Please input your token.");
        panel.add(tokenText);
        String token = ConfigFile.get("settings", "token");
        tokenText.setText(token);

        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel;
    }

    @Override
    protected ValidationInfo doValidate() {
        // TODO 调用远程接口做校验

        String id = idText.getText();
        String token = tokenText.getText();
        if ("webx".equals(id) && "8ba394513f8420e".equals(token)) {
            return null;
        }

        if ("aborn".equals(id) && "0x8bf8e412".equals(token)) {
            return null;
        }

        return new ValidationInfo("Invalid user id or token.");
    }

    @Override
    public void doOKAction() {
        ConfigFile.set("settings", "id", idText.getText());
        ConfigFile.set("settings", "token", tokenText.getText());
        super.doOKAction();
    }

}
