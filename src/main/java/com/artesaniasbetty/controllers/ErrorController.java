package com.artesaniasbetty.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ErrorController {
    public Button btnMinimize;
    public Button btnClose;
    public Label messageTop;
    public FlowPane barTop;
    public Button btnCancel;
    public Label txtMessage;

    public void setData(boolean typeError, String message){
        if(typeError){
            barTop.setStyle("-fx-background-color:rgb(255, 15, 15); -fx-text-fill: white");
            messageTop.setText("Error");
        }else {
            barTop.setStyle("-fx-background-color: green; -fx-text-fill: white");
            messageTop.setText("Info");
        }
        txtMessage.setText(message);
    }
    public void minimizeApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnMinimize.getScene().getWindow();
        currentStage.setIconified(true);
    }

    public void closeApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    public void cancelOperation(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }
}
