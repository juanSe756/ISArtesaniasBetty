package com.artesaniasbetty;

import com.artesaniasbetty.controllers.UsuarioController;
import com.artesaniasbetty.gui.StartFrame;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

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
//                sf.getLoginPnl().getLogin().setIcon(new ImageIcon(getClass().getResource("/assets/loadingx28.gif")));
                                authorizeLogin();
                                    break;
        }
    }
    private void authorizeLogin(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT u.contrasena FROM Usuario u WHERE u.nickname = :username");
        q.setParameter("username",sf.getLoginPnl().getUsername());
        String pass = String.valueOf(q.getSingleResult());
        if(uc.verifyPassword(sf.getLoginPnl().getPassword(),pass)){
            sf.setLogined(true);
            sf.login();
            System.out.println("Logined");
//            sf.dispose();
        }
    }
}