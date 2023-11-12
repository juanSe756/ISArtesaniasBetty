package com.artesaniasbetty.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{
    public Button btnInicio;

    @FXML
    public GridPane gridProducts;

    public void changeBackground(ActionEvent actionEvent) {
        btnInicio.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");

    }

    public void inicializar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/onlyProduct.fxml"));
            FlowPane product = loader.load();

            GridPane.setColumnIndex(product, 0);
            gridProducts.getChildren().add(product);
            gridProducts.getColumnConstraints().get(0).setPercentWidth(33);
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("views/onlyProduct.fxml"));
            FlowPane product1 = loader1.load();

            GridPane.setColumnIndex(product1, 1);
            gridProducts.getChildren().add(product1);
            gridProducts.getColumnConstraints().get(1).setPercentWidth(33);
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("views/onlyProduct.fxml"));
            FlowPane product2 = loader2.load();

            GridPane.setColumnIndex(product2, 2);
            gridProducts.getChildren().add(product2);
            gridProducts.getColumnConstraints().get(2).setPercentWidth(33);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializar();
    }
}
