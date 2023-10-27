package com.artesaniasbetty.gui.customs;

import javax.swing.*;
import java.awt.*;

public class ClearMiniButton extends JButton {
    public ClearMiniButton(ImageIcon img, int width, int height){
            super();
            Dimension d = new Dimension(width,height);
            setPreferredSize(d);
            Icon i = new ImageIcon(img.getImage().getScaledInstance(d.getSize().width,d.getSize().height,Image.SCALE_DEFAULT));
            this.setIcon(i);
            this.setOpaque(false);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
        }
}
