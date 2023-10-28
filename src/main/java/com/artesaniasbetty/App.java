package com.artesaniasbetty;

import com.artesaniasbetty.gui.StartFrame;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App implements ActionListener {
    StartFrame sf;
    public App() {
        sf = new StartFrame(this);
    }
    public static void main(String[] args) {
        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "SEE_PASSWORD":    sf.managePasswordButton();
        }
    }
}