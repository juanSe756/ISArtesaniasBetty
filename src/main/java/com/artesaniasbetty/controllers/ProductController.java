package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.dao.VentaDAO;
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
    public ImageView imageStock;
    public ImageView imageTrend;
    ProductoDAO productoDAO = new ProductoDAO();
    VentaDAO ventaDAO = new VentaDAO();


    public void setData(Producto producto) {
        nameProduct.setText(producto.getNombre());
        amountProduct.setText("Cantidad = " + producto.getStock());
        productoDAO.convertBytesToImage(producto.getFoto(), producto.getNombre());
        //loadImageTrend(producto.getId());
        loadImageStock(producto);
        loadImage();
    }

    private void loadImageTrend(int idProduct){
        int amountSales = ventaDAO.getQuantitySoldByProduct(idProduct);
        if(amountSales <= 2 ){
            imageTrend.setImage(new Image("/assets/down.png"));
        }
    }

    private void loadImageStock(Producto producto){
        int stockProduct = producto.getStock();
        if(stockProduct <= 0 ){
            imageStock.setImage(new Image("/assets/warning.png"));
        } else if (stockProduct <= 5 && stockProduct > 0) {
            imageStock.setImage(new Image("/assets/exclamation.png"));
        }
    }

    public void loadImage() {
        String enlace = ProductoDAO.outputPath;
        enlace = enlace.replace("src/main/resources","");
        imageProduct.setImage(new Image(enlace));
    }
}
