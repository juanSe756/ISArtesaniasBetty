package com.artesaniasbetty;

import com.artesaniasbetty.controllers.StageLoader;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
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
        // Deshabilitar el botón de inicio de sesión para evitar múltiples intentos mientras se procesa
        sf.getLoginPnl().getLogin().setEnabled(false);

        // Mostrar mensaje de "cargando"
        mostrarCargando(() -> {
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

                    // Ejecutar operaciones adicionales después del inicio de sesión en el hilo de la aplicación de JavaFX
                    Platform.runLater(() -> {
                        showMainStage();
                        // Puedes agregar aquí cualquier otra operación que necesites realizar después del inicio de sesión
                    });
                } else {
                    // Mostrar mensaje de error en la interfaz de usuario
                    Platform.runLater(() -> sf.getLoginPnl().setLoginInfo("Contraseña incorrecta"));
                }
            } catch (NoResultException e) {
                // Mostrar mensaje de error en la interfaz de usuario
                Platform.runLater(() -> sf.getLoginPnl().setLoginInfo("Usuario no encontrado"));
            } finally {
                // Habilitar el botón de inicio de sesión después de procesar
                Platform.runLater(() -> sf.getLoginPnl().getLogin().setEnabled(true));
            }
        });
    }

    private void mostrarCargando(Runnable task) {
        // Crear una nueva ventana emergente de progreso
        Platform.runLater(() -> {
            Stage progressDialog = new Stage();
            progressDialog.initStyle(StageStyle.UNDECORATED);
            progressDialog.initModality(Modality.APPLICATION_MODAL);

            // Crear un indicador de progreso
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setProgress(-1.0); // Indeterminado

            // Crear un panel apilado con el indicador de progreso
            StackPane stackPane = new StackPane(progressIndicator);

            // Configurar la ventana emergente
            progressDialog.setScene(new Scene(stackPane, 200, 200));
            progressDialog.show();

            // Iniciar la tarea en segundo plano
            Thread backgroundTask = new Thread(() -> {
                try {
                    // Ejecutar el código de la tarea en el hilo de la aplicación de JavaFX
                    Platform.runLater(() -> task.run());
                } finally {
                    // Cerrar la ventana emergente cuando haya terminado la tarea
                    Platform.runLater(() -> progressDialog.close());
                }
            });
            backgroundTask.start();
        });
    }



    private void showMainStage() {
        mostrarCargando(() -> {
            try {
                // Realizar las operaciones relacionadas con la visualización de la ventana principal
                // Por ejemplo, cargar el archivo FXML y mostrar la ventana principal
                Stage mainStage = new Stage();
                Parent root1 = FXMLLoader.load(getClass().getResource("/archivesViews/home.fxml"));
                Screen screen = Screen.getPrimary();
                javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
                Scene scene1 = new Scene(root1, bounds.getWidth(), bounds.getHeight());
                Image icono = new Image("/assets/logo.png");
                mainStage.getIcons().add(icono);
                mainStage.setFullScreen(true);
                mainStage.initStyle(StageStyle.UNDECORATED);
                mainStage.setScene(scene1);
                mainStage.show();

                // Puedes agregar aquí cualquier otra operación que necesites realizar después de mostrar la ventana principal
            } catch (IOException e) {
                e.printStackTrace(); // Manejar la excepción según tu caso
            } finally {
                // Cierra la ventana emergente de "cargando" después de completar las operaciones
                Platform.runLater(() -> StageLoader.closeProgress());
            }
        });
    }


}