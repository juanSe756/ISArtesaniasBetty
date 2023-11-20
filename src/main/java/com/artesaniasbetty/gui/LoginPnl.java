package com.artesaniasbetty.gui;

import com.artesaniasbetty.gui.customs.*;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginPnl extends JPanel {
    private JButton logo;
    @Getter
    private JButton login;
    private JLabel title;
    private JLabel loginInfo;

    private JLabel title2;
    private JLabel loginLbl;
    private JLabel userTag;
    private JTextField username;
    private JLabel passwordTag;
    private JPasswordField password;

    @Getter
    private JButton eyeButton;
    private int radius;
    public LoginPnl(ActionListener a){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        radius=20;
        setOpaque(false);
        initComponents(a);
    }
    private void initComponents(ActionListener a){
        GradientPaint blues = new GradientPaint(0,0,new Color(0, 120, 218),140,17,new Color(46, 192, 255),false);
        Font fontButton = new Font("Inter",Font.BOLD,13);
        Font fontSubtitle = new Font("Inter",Font.BOLD,15);
        Font fontTitle = new Font("Inter",Font.BOLD,20);
        login = new RoundButton("Ingresar",18,100,45,fontButton,blues);
        login.setActionCommand("LOGIN");
        login.addActionListener(a);
        logo = new ClearMiniButton(new ImageIcon(getClass().getResource("/assets/logo.png")),100,100);
        //logo.setEnabled(false);
        title = new MyJLabel("Artesanías",fontTitle,Color.BLACK);
        title2 = new MyJLabel("Betty",fontTitle,Color.BLACK);
        loginInfo = new MyJLabel(" ",fontSubtitle,Color.RED);
        userTag = new MyJLabel("Usuario",fontSubtitle,Color.BLACK);
        username = new MinimalTextField(fontSubtitle,Color.BLACK, "Ejm: b3atriz09",new Dimension(200,30));
        username.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (username.getText().length() >= 15 ) // limit textfield to 15 characters
                    e.consume();
            }
        });
        passwordTag = new MyJLabel("Contraseña",fontSubtitle,Color.BLACK);
        password = new MinimalTextPassword(fontSubtitle,Color.BLACK,new Dimension(200,30));
        password.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (password.getPassword().length >= 20 ) // limit textpassword to 20 characters
                    e.consume();
            }
        });
        loginLbl = new MyJLabel("Inicia sesión",fontSubtitle,Color.BLACK);
        eyeButton = new ClearMiniButton(new ImageIcon(getClass().getResource("/assets/eye-off.png")),28,28);
        eyeButton.setActionCommand("SEE_PASSWORD");
        eyeButton.addActionListener(a);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx=GridBagConstraints.HORIZONTAL;
        gbc.weighty=GridBagConstraints.NORTH;
        //gbc.anchor=GridBagConstraints.FIRST_LINE_START;
        gbc.insets.set(10,0,0,0);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=0;
        add(new JLabel(" "),gbc);
        gbc.insets.set(0,0,0,0);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=1;
        add(logo,gbc);
        gbc.insets.set(0,0,0,0);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=2;
        add(title,gbc);
        gbc.insets.set(0,0,0,0);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=3;
        add(title2,gbc);
        gbc.insets.set(0,0,0,0);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=4;
        add(loginLbl,gbc);
        gbc.insets.set(30,0,0,0);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=4;
        add(loginInfo,gbc);
        gbc.anchor=GridBagConstraints.FIRST_LINE_START;
        gbc.insets.set(10,25,0,0);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=5;
        add(userTag,gbc);
        gbc.insets.set(20,25,0,0);
        gbc.ipady=0;
        gbc.ipadx=200;
        gbc.gridx=0;
        gbc.gridy=6;
        add(username,gbc);
        gbc.insets.set(30,25,0,0);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=7;
        add(passwordTag,gbc);
        gbc.insets.set(20,25,0,0);
        gbc.ipady=0;
        gbc.ipadx=150;
        gbc.gridx=0;
        gbc.gridy=8;
        add(password,gbc);
//        gbc.anchor=GridBagConstraints.LAST_LINE_END;
        gbc.insets.set(15,200,0, 25);
        gbc.ipady=0;
        gbc.ipadx=0;
        gbc.gridx=0;
        gbc.gridy=8;
        add(eyeButton,gbc);
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets.set(20,0,90,0);
        gbc.ipady=0;
        gbc.ipadx=30;
        gbc.gridx=0;
        gbc.gridy=9;
        add(login,gbc);
    }
    protected void paintComponent(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.getBackground());
        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
        super.paintComponent(graphics);
    }
    public void setLoginInfo(String info){
        loginInfo.setText(info);
    }
    public String getUsername() {
        return username.getText();
    }
    public JPasswordField getPasswordField() {
        return password;
    }
    public String getPassword(){
        return new String(getPasswordField().getPassword());
    }
}