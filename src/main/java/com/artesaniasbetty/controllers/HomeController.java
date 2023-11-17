package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.model.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class HomeController implements Initializable{
    @FXML
    public Button btnInicio;
    @FXML
    public ScrollPane scrollProducts;
    public FlowPane flowProducts;
    public Button btnProducts;
    public Button btnReportes;
    public BorderPane borderHome;
    public HBox hBoxBottom;
    public Button btnAlabaster;
    public Button btnRegistrarVenta;
    public Button btnVentas;
    private ProductoDAO productoDAO;

    public void inicializar() {
        productoDAO = new ProductoDAO();
        List<Producto> products = productoDAO.getAllProducts();
        FlowPane flowPane = new FlowPane();
        flowPane.setVgap(10);
        flowPane.setHgap(10);

        scrollProducts.setContent(flowPane);
        borderHome.setCenter(scrollProducts);
        borderHome.setBottom(hBoxBottom);
        ProductController productController = null;
        for (int i = 0; i < products.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/onlyProduct.fxml"));
                FlowPane productTemplate = loader.load();
                productController = loader.getController();
                productController.setData(products.get(i));
                flowPane.getChildren().add(productTemplate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializar();
    }

    public void changeBackground(ActionEvent actionEvent) {
        btnInicio.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        inicializar();
    }

    public void changeViewProducts(ActionEvent actionEvent) {
        btnProducts.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/products.fxml"));
            AnchorPane anchorPaneContent = loader.load();
            borderHome.setCenter(anchorPaneContent);
            borderHome.setBottom(null);
            fillTable(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTable(FXMLLoader loader) {
        ProductoDAO productoDAO = new ProductoDAO();
        MainProductController mainProductController = new MainProductController();
        try {
            mainProductController = loader.getController();
            for (StringBuilder s : productoDAO.getProductsTable()) mainProductController.setData(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changeViewReports(ActionEvent actionEvent) {
        btnReportes.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/reports.fxml"));
            BorderPane borderPane = loader.load();
            borderHome.setCenter(borderPane.getCenter());
            borderHome.setBottom(borderPane.getBottom());
            borderHome.setLeft(borderPane.getLeft());
            borderHome.setTop(borderPane.getTop());
            borderHome.setRight(borderPane.getRight());
            initComponentsReports(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeViewResupply(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/resupply.fxml"));
        BorderPane panel = null;
        try {
            panel = loader.load();
            Scene scene = new Scene(panel, 450, 350);

            // Obtén la ventana actual y muestra el nuevo panel en una ventana emergente
            Scene currentScene = btnRegistrarVenta.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            Stage panelStage = new Stage();
            panelStage.setScene(scene);
            panelStage.initModality(Modality.APPLICATION_MODAL); // Esto hará que la nueva ventana sea modal
            panelStage.initOwner(stage); // Establece la ventana principal como propietaria de la nueva ventana
            initComponentsResupply(loader);
            panelStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initComponentsResupply(FXMLLoader loader){
        ProductoDAO productoDAO = new ProductoDAO();
        ResupplyController resupplyController = null;
        try {
            resupplyController = loader.getController();
            resupplyController.initComponents(productoDAO.getAllProducts());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initComponentsReports(FXMLLoader loader){
        ProductoDAO productoDAO = new ProductoDAO();
        ReportsController reportsController = null;
        try {
            reportsController = loader.getController();
            reportsController.init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changeViewRegisterSale(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/SaleWindow.fxml"));
        BorderPane panel = null;
        try {
            panel = loader.load();
            Scene scene = new Scene(panel, 500, 600);

            // Obtén la ventana actual y muestra el nuevo panel en una ventana emergente
            Scene currentScene = btnRegistrarVenta.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            Stage panelStage = new Stage();
            panelStage.setScene(scene);
            panelStage.initModality(Modality.APPLICATION_MODAL); // Esto hará que la nueva ventana sea modal
            panelStage.initOwner(stage); // Establece la ventana principal como propietaria de la nueva ventana

            panelStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeViewSales(ActionEvent actionEvent) {
        btnVentas.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/history.fxml"));
            BorderPane borderPane = loader.load();
            borderHome.setCenter(borderPane.getCenter());
            borderHome.setBottom(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
