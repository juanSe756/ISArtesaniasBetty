package com.artesaniasbetty.controllers;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageLoader {
    private static Stage progressDialog;

    public static void showProgress() {
        Platform.runLater(() -> {
            progressDialog = new Stage();
            progressDialog.initStyle(StageStyle.UNDECORATED);
            progressDialog.initModality(Modality.APPLICATION_MODAL);

            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setProgress(-1.0);

            StackPane stackPane = new StackPane(progressIndicator);

            progressDialog.setScene(new Scene(stackPane, 200, 200));
            progressDialog.show();
        });
    }

    public static void closeProgress() {
        Platform.runLater(() -> {
            if (progressDialog != null) {
                progressDialog.close();
            }
        });
    }
}

