package com.artesaniasbetty;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("views/SaleWindow.fxml"));
//        Parent root1 = FXMLLoader.load(getClass().getResource("views/resupply.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("/archivesViews/onlyProduct.fxml"));

//        Scene scene = new Scene(root, 500, 600);
//        Scene scene1 = new Scene(root1, 450, 350);
        Scene scene2 = new Scene(root2, 500, 550);

        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.setScene(scene1);


        /////////////////////////////////


        stage.setScene(scene2);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}