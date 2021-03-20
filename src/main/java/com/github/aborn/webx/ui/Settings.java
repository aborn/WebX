/* ==========================================================
File:        Settings.java
Description: Prompts user for api key if it does not exist.
Maintainer:  WakaTime <support@wakatime.com>
License:     BSD, see LICENSE for more details.
Website:     https://wakatime.com/
===========================================================*/

package com.github.aborn.webx.ui;

import com.github.aborn.webx.datatypes.PlaceholderTextField;
import com.github.aborn.webx.modules.tc.TimeTraceLogger;
import com.github.aborn.webx.modules.tc.transfer.DataSenderHelper;
import com.github.aborn.webx.modules.tc.transfer.SenderResponse;
import com.github.aborn.webx.modules.tc.transfer.ServerInfo;
import com.github.aborn.webx.utils.ConfigFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.ValidationInfo;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class Settings extends DialogWrapper {
    private final JPanel panel;
    private final JLabel idLabel;
    private final PlaceholderTextField idText;
    private final JLabel tokenLabel;
    private final PlaceholderTextField tokenText;
    private Project project;

    public Settings(@Nullable Project project) {
        super(project, true);

        this.project = project;
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

    /**
     * 注意这里的校验要简单，否则会出现频繁校验的问题
     * @return
     */
    @Override
    protected ValidationInfo doValidate() {
        String id = idText.getText();
        String token = tokenText.getText();
        if ("webx".equals(id) && "8ba394513f8420e".equals(token)) {
            return null;
        }

        if (StringUtils.isNoneBlank(token) && token.length() == 10 && token.startsWith("0x")) {
            return null;
        }

        return new ValidationInfo("Invalid user id or token.");
    }

    @Override
    public void doOKAction() {
        String id = idText.getText();
        String token = tokenText.getText();

        SenderResponse senderResponse = DataSenderHelper.validate(id, token);
        if (senderResponse.getCode() == 200 && senderResponse.getStatus()) {
            // 校验成功保存到本地
            TimeTraceLogger.info("settings is success, token: " + token + ", id: " + id);
            ConfigFile.set("settings", "id", id);
            ConfigFile.set("settings", "token", token);
            ServerInfo.setToken(token);
            super.doOKAction();
        } else {
            TimeTraceLogger.info("settings is error, token: " + token + ", id: " + id);
            Messages.showMessageDialog(this.project,
                    "Invalid user id or token！ErrorCode:" + senderResponse.getCode() + "," + senderResponse.getMessage(),
                    "Error", Messages.getInformationIcon());
            return;
        }
    }

}
