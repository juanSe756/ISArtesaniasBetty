package com.artesaniasbetty.gui.customs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundButton extends JButton {
    private int radius;
    private GradientPaint deg;
    public RoundButton(String txt, int radius,int width,int height,Font font,GradientPaint deg){
        super(txt);
        this.setForeground(Color.WHITE);
        this.setFont(font);
        this.setPreferredSize(new Dimension(width,height));
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.radius=radius;
        this.deg=deg;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setDeg(new GradientPaint(0,0,new Color(255, 0, 61, 255),140,17,new Color(252, 0, 143),false));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setDeg(deg);
            }
        });
    }

    public void setDeg(GradientPaint deg) {
        this.deg = deg;
    }

    @Override
    protected void paintComponent(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(deg);
        g2.fillRoundRect(5,0,getWidth()-10,getHeight(),radius,radius);
        super.paintComponent(graphics);
    }
}
