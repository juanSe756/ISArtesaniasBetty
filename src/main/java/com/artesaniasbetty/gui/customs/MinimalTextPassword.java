package com.artesaniasbetty.gui.customs;

import javax.swing.*;
import java.awt.*;
//Panel para TextPassword customizado
public class MinimalTextPassword extends JPasswordField {
    Color borderColor;
    public MinimalTextPassword(Font font, Color borderColor){
        setFont(font);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
        //Asterisco para tener seguridad :o
        setEchoChar('*');
        this.borderColor=borderColor;
    }
    protected void paintComponent(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.drawLine(0,getHeight()-1,getWidth(),getHeight()-1);
        super.paintComponent(graphics);
    }
}
