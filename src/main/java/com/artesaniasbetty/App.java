package com.artesaniasbetty;

import com.artesaniasbetty.controllers.EntityMF;
import com.artesaniasbetty.controllers.UsuarioController;
import com.artesaniasbetty.gui.StartFrame;
import jakarta.persistence.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App implements ActionListener {
    StartFrame sf;
    UsuarioController uc;
    public App() {
        sf = new StartFrame(this);
        uc = new UsuarioController();
    }
    public static void main(String[] args) {
        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "SEE_PASSWORD":    sf.managePasswordButton();
                                    break;
            case "LOGIN":
                sf.getLoginPnl().getLogin().setIcon(new ImageIcon(getClass().getResource("/assets/loadingx28.gif")));
                                authorizeLogin();
                                    break;
        }
    }
    private void authorizeLogin() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            String username = sf.getLoginPnl().getUsername();
            String password = sf.getLoginPnl().getPassword();
            TypedQuery<String> query = em.createQuery("SELECT u.contrasena FROM Usuario u WHERE u.nickname = :username", String.class);
            query.setParameter("username", username);
            String storedPassword = query.getSingleResult();
            if (uc.verifyPassword(password, storedPassword)) {
                sf.setLogined(true);
                sf.login();
                System.out.println("Logined");
                // sf.dispose();
            } else {
                System.out.println("Wrong password");
            }
        } catch (NoResultException e) {
            System.out.println("User not found");
        }
    }

}