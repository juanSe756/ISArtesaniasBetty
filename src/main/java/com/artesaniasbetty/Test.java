package com.artesaniasbetty;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Test extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("/archivesViews/home.fxml"));
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
        Scene scene1 = new Scene(root1, bounds.getWidth(), bounds.getHeight());
        stage.setFullScreen(true);
        stage.ini--module-path "C:\Users\Jorge\Downloads\openjfx-21.0.1_windows-x64_bin-sdk\javafx-sdk-21.0.1\lib" --add-modules javafx.controls,javafx.fxmltStyle(StageStyle.UNDECORATED);
        stage.setScene(scene1);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}