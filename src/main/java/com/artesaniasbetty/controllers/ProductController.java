package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.model.Producto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController {
    @FXML
    public ImageView imageProduct;
    @FXML
    public Label nameProduct;
    @FXML
    public Label amountProduct;
    @FXML
    public FlowPane productPane;


    public void setData(Producto producto) {
        ProductoDAO productoDAO = new ProductoDAO();
        nameProduct.setText(producto.getNombre());
        amountProduct.setText("Cantidad = " + producto.getStock());
        productoDAO.convertBytesToImage(producto.getFoto(), producto.getNombre());
        loadImage();
    }

    public void loadImage() {
        String enlace = ProductoDAO.outputPath;
        enlace = enlace.replace("src/main/resources","");
        System.out.println(enlace);
        imageProduct.setImage(new Image(enlace));
    }
}
