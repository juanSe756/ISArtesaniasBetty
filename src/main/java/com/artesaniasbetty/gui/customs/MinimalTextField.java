package com.artesaniasbetty.gui.customs;

import javax.swing.*;
import java.awt.*;
//Panel para TextField customizado
public class MinimalTextField extends JTextField {
    Color borderColor;
    public MinimalTextField(Font font, Color borderColor, String placeholder){
        new TextPrompt(placeholder,this);
        setFont(font);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
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
