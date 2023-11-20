package com.artesaniasbetty.gui.customs;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClosePanel extends JPanel{
    private JButton buttonMin;
    private JButton buttonClose;
    public ClosePanel(ActionListener a){
        setLayout(new GridLayout(1,20));
        setBackground(Color.BLACK);
        initComponents(a);
    }
    private void initComponents(ActionListener a){
        buttonMin = new ClearMiniButton(new ImageIcon(getClass().getResource("/assets/minus-small.png")), 20, 20);
        buttonMin.setActionCommand("MINIMIZE");
        buttonMin.addActionListener(a);
        buttonClose = new ClearMiniButton(new ImageIcon(getClass().getResource("/assets/cerrar.png")), 20, 20);
        buttonClose.setActionCommand("CLOSE");
        buttonClose.addActionListener(a);
        add(new JLabel(" "),0);
        add(new JLabel(" "),1);
        add(new JLabel(" "),2);
        add(new JLabel(" "),3);
        add(new JLabel(" "),4);
        add(new JLabel(" "),5);
        add(new JLabel(" "),6);
        add(new JLabel(" "),7);
        add(new JLabel(" "),8);
        add(new JLabel(" "),9);
        add(new JLabel(" "),10);
        add(new JLabel(" "),11);
        add(new JLabel(" "),12);
        add(new JLabel(" "),13);
        add(new JLabel(" "),14);
        add(new JLabel(" "),15);
        add(new JLabel(" "),16);
        add(new JLabel(" "),17);
        add(new JLabel(" "),18);
        add(new JLabel(" "),19);
        add(new JLabel(" "),20);
        add(buttonMin, 21);
//        gbc.insets.set(0, 440, 0, 0);
//        gbc.ipady = 0;
//        gbc.ipadx = 0;
//        gbc.gridx = 1;
//        gbc.gridy = 0;
        add(buttonClose,22);
    }

}
