package com.artesaniasbetty.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import lombok.Getter;

public class WarningController {
    public Button btnMinimize;
    public Button btnClose;
    public Label messageTop;
    public FlowPane barTop;
    public Button btnCancel;
    public Label txtMessage;
    public Button btnConfirmar;
    @Getter
    boolean selected;

    public void setData(String message){
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

    public boolean confirmarOperation(ActionEvent actionEvent) {
        selected = true;
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
