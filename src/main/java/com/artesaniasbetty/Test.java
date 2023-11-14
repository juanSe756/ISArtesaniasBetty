package com.artesaniasbetty;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/archivesViews/history.fxml"));
        Parent root1 = FXMLLoader.load(getClass().getResource("/archivesViews/home.fxml"));
//        Parent root2 = FXMLLoader.load(getClass().getResource("views/products.fxml"));
//        Parent root3 = FXMLLoader.load(getClass().getResource("views/reports.fxml"));
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();

//        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        Scene scene1 = new Scene(root1, bounds.getWidth(), bounds.getHeight());
//        Scene scene2 = new Scene(root2, bounds.getWidth(), bounds.getHeight());
//        Scene scene3 = new Scene(root3, bounds.getWidth(), bounds.getHeight());

//        stage.setScene(scene);
        stage.setScene(scene1);
//        stage.setScene(scene2);
//        stage.setScene(scene3);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}