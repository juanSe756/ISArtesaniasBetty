package com.artesaniasbetty;

import com.artesaniasbetty.dao.EntityMF;
import com.artesaniasbetty.dao.UsuarioDAO;
import com.artesaniasbetty.gui.StartFrame;
import com.artesaniasbetty.model.Usuario;
import jakarta.persistence.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App implements ActionListener {
    StartFrame sf;
    UsuarioDAO uc;

    public static Usuario usuarioLogeado = null;
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
        }
    }
    private void authorizeLogin() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            String username = sf.getLoginPnl().getUsername();
            String password = sf.getLoginPnl().getPassword();
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nickname = :username", Usuario.class);
            query.setParameter("username", username);
            Usuario usuario = query.getSingleResult();
            String storedPassword = usuario.getContrasena();
            if (uc.verifyPassword(password, storedPassword)) {
                usuarioLogeado = usuario;
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