package com.artesaniasbetty.gui;

import com.artesaniasbetty.gui.customs.ClearMiniButton;
import com.artesaniasbetty.gui.customs.ClosePanel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class StartFrame extends JFrame {
    private boolean isEyeEnabled = false;
    @Getter
    @Setter
    private boolean logined = false;
    @Getter
    private LoginPnl loginPnl;
    private JLabel label;


    private JPanel closePnl;
    public StartFrame(ActionListener a) {
        setTitle("Artesanias Betty");
        setUndecorated(true);
        setLayout(new GridBagLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        getContentPane().setBackground(new Color(194, 168, 148));
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/assets/logo.png"))).getImage());
        initComponents(a);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void initComponents(ActionListener a) {
        loginPnl = new LoginPnl(a);
        closePnl = new ClosePanel(a);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx=GridBagConstraints.HORIZONTAL;
        gbc.weighty=GridBagConstraints.NORTH;
        gbc.insets.set(0, 0, this.getHeight()-20, 0);
        gbc.ipady = 30;
        gbc.ipadx = this.getWidth();
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(closePnl, gbc);
        gbc.insets.set(0, 0, 0, 0);
        gbc.ipady = 0;
        gbc.ipadx = 100;
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(loginPnl, gbc);
    }

    public void managePasswordButton() {
        if (!isEyeEnabled) {
            isEyeEnabled = true;
            loginPnl.getEyeButton().setIcon(
                    new ImageIcon(Objects.requireNonNull(getClass()
                            .getResource("/assets/eye.png"))));
            loginPnl.getPasswordField().setEchoChar((char) 0);
        } else {
            isEyeEnabled = false;
            loginPnl.getEyeButton().setIcon(
                    new ImageIcon(Objects.requireNonNull(getClass()
                            .getResource("/assets/eye-off.png"))));
            loginPnl.getPasswordField().setEchoChar('*');
        }
    }
    public void login(){
        if(logined){
            remove(loginPnl);
            this.dispose();
        }
    }

}
