package com.artesaniasbetty.gui.customs;

import javax.swing.*;
import java.awt.*;

public class MyJLabel extends JLabel {
    public MyJLabel(String text, Font font,Color color){
        super(text);
        setFont(font);
        setForeground(color);
    }
}
