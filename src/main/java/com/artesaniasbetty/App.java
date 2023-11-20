package com.artesaniasbetty;

import com.artesaniasbetty.dao.EntityMF;
import com.artesaniasbetty.dao.UsuarioDAO;
import com.artesaniasbetty.gui.StartFrame;
import com.artesaniasbetty.model.Usuario;
import jakarta.persistence.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class App  extends Application implements ActionListener{
    StartFrame sf;
    UsuarioDAO uc;

    public static Usuario usuarioLogeado = null;
    public App() {
        sf = new StartFrame(this);
        uc = new UsuarioDAO();
    }

    public void start(Stage stage) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("/archivesViews/home.fxml"));
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
        Scene scene1 = new Scene(root1, bounds.getWidth(), bounds.getHeight());
        stage.setScene(scene1);
        stage.show();
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
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nickname = :username", Usuario.class);
            query.setParameter("username", username);
            Usuario usuario = query.getSingleResult();
            String storedPassword = usuario.getContrasena();
            if (uc.verifyPassword(password, storedPassword)) {
                usuarioLogeado = usuario;
                sf.setLogined(true);
                sf.login();
                System.out.println("Logined");
                //sf.dispose();
                showMainStage();
            } else {
                sf.getLoginPnl().setLoginInfo("Contraseña incorrecta");
                sf.getLoginPnl().getLogin().setEnabled(true);
            }
        } catch (NoResultException e) {
            sf.getLoginPnl().setLoginInfo("Usuario no encontrado");
            sf.getLoginPnl().getLogin().setEnabled(true);
        }
    }

    private void showMainStage() {
        Platform.runLater(() -> {
            Stage mainStage = new Stage();
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("/archivesViews/home.fxml"));
                Screen screen = Screen.getPrimary();
                javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
                Scene scene1 = new Scene(root1, bounds.getWidth(), bounds.getHeight());
                mainStage.setFullScreen(true);
                mainStage.initStyle(StageStyle.UNDECORATED);
                mainStage.setScene(scene1);
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Manejar la excepción según tu caso
            }
        });
    }

}