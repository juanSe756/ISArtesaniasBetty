package com.artesaniasbetty;

import com.artesaniasbetty.dao.EntityMF;
import com.artesaniasbetty.dao.UsuarioDAO;
import com.artesaniasbetty.gui.StartFrame;
import jakarta.persistence.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App implements ActionListener {
    StartFrame sf;
    UsuarioDAO uc;
    public App() {
        sf = new StartFrame(this);
        uc = new UsuarioDAO();
    }
    public static void main(String[] args) {
        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "SEE_PASSWORD":    sf.managePasswordButton();
                                    break;
            case "LOGIN":           sf.getLoginPnl().getLogin().setEnabled(false);
                authorizeLogin();
                                    break;
            case "MINIMIZE":        sf.setState(sf.ICONIFIED);
                                    break;
            case "CLOSE":           sf.dispose();
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
                sf.getLoginPnl().setLoginInfo("Contraseña incorrecta");
                sf.getLoginPnl().getLogin().setEnabled(true);
            }
        } catch (NoResultException e) {
            sf.getLoginPnl().setLoginInfo("Usuario no encontrado");
            sf.getLoginPnl().getLogin().setEnabled(true);
        }
    }

}