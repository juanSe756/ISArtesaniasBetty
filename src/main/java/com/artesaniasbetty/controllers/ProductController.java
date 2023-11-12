package com.artesaniasbetty.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    public ImageView imageProduct;
    public Label nameProduct;
    public Label amountProduct;

    Product product;

    public void initialize() {
        product = new Product("Canasto",1);
        mostrarTitulo();
    }

    private void mostrarTitulo() {
        nameProduct.setText(product.getName());
        amountProduct.setText("Cantidad = "+ product.getAmount());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }
}
