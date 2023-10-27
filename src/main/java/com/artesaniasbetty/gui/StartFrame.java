package com.artesaniasbetty.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class StartFrame extends JFrame {
    private boolean isEyeEnabled = false;
    private LoginPnl loginPnl;

    public StartFrame(ActionListener a) {
        setTitle("Artesanias Betty");
        setLayout(new GridBagLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        getContentPane().setBackground(new Color(85, 55, 57));

        initComponents(a);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void initComponents(ActionListener a) {
        loginPnl = new LoginPnl(a);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(0, 0, 0, 0);
        gbc.ipady = 0;
        gbc.ipadx = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(loginPnl, gbc);
    }

    public void managePasswordButton() {
        if (!isEyeEnabled) {
            System.out.println("if"+isEyeEnabled);
            isEyeEnabled = true;
            loginPnl.getEyeButton().setIcon(
                    new ImageIcon(Objects.requireNonNull(getClass()
                            .getResource("/assets/eye.png"))));
            loginPnl.getPassword().setEchoChar((char) 0);
        } else {
            System.out.println("else"+isEyeEnabled);
            isEyeEnabled = false;
            loginPnl.getEyeButton().setIcon(
                    new ImageIcon(Objects.requireNonNull(getClass()
                            .getResource("/assets/eye-off.png"))));
            loginPnl.getPassword().setEchoChar('*');
        }
    }
}
