package com.artesaniasbetty.controllers;

import com.artesaniasbetty.App;
import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.gui.StartFrame;
import com.artesaniasbetty.model.Producto;
import com.artesaniasbetty.model.Venta;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Button btnCerrar;
    public Button btnMinimizar;
    public Button btnExit;
    private ProductoDAO productoDAO;

    public void inicializar() {
        btnInicio.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        btnVentas.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnProducts.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnReportes.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        productoDAO = new ProductoDAO();
        List<Producto> products = productoDAO.getAllProducts();
        FlowPane flowPane = new FlowPane();
        flowPane.setVgap(10);
        flowPane.setHgap(10);

        scrollProducts.setContent(flowPane);
        borderHome.setCenter(scrollProducts);
        borderHome.setBottom(hBoxBottom);
        ProductController productController = null;
        loadImage(productoDAO);
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

    private void loadImage(ProductoDAO productoDAO){
        List<Producto> listProductos = productoDAO.getAllProducts();
        for (Producto producto : listProductos) {
            productoDAO.convertBytesToImage(producto.getFoto(), producto.getNombre());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializar();
    }

    public void changeBackground(javafx.scene.input.MouseEvent mouseEvent) {
        btnInicio.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        btnVentas.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnProducts.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnReportes.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        inicializar();
    }

    public void backChangeBackground(MouseEvent mouseEvent) {
        btnInicio.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
    }


    public void changeViewProducts(javafx.scene.input.MouseEvent mouseEvent) {
        btnProducts.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        btnVentas.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnInicio.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnReportes.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/products.fxml"));
            ScrollPane scrollPane = loader.load();
            borderHome.setCenter(scrollPane);
            borderHome.setBottom(null);
            fillTable(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backChangeViewProducts(MouseEvent mouseEvent) {
        btnProducts.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
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

    public void changeViewReports(javafx.scene.input.MouseEvent mouseEvent) {
        btnReportes.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        btnVentas.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnProducts.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnInicio.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/reports.fxml"));
            ScrollPane scroll = loader.load();
            borderHome.setCenter(scroll);
            initComponentsReports(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backChangeViewReports(MouseEvent mouseEvent) {
        btnReportes.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
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
            panelStage.initStyle(StageStyle.UNDECORATED);
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

    public void showLoadingWindow() {
        // Crea una ventana de carga
        Stage loadingStage = new Stage();
        loadingStage.initModality(Modality.APPLICATION_MODAL);

        // Carga el FXML de la ventana de carga
        try {
            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/archivesViews/LoadingWindow.fxml"));
            Parent loadingRoot = loadingLoader.load();
            Scene loadingScene = new Scene(loadingRoot);
            loadingStage.setScene(loadingScene);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadingStage.initStyle(StageStyle.UNDECORATED);
        // Muestra la ventana de carga
        loadingStage.show();
    }

    public void hideLoadingWindow(Stage loadingStage) {
        // Oculta la ventana de carga
        Platform.runLater(() -> loadingStage.hide());
    }

    public void initComponentsReports(FXMLLoader loader) {
        VentaDAO ventaDAO = new VentaDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        ReportsController reportsController = null;
        try {
            reportsController = loader.getController();
            reportsController.init(ventaDAO.getTop5Products());
            reportsController.setData(ventaDAO.getIncomeThisMonth(), (int) productoDAO.getProductsCreatedThisMonth(),
                    ventaDAO.getSalesThisMonth().size(), productoDAO.getAllProducts().size());
        } catch (Exception e) {
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
            initComponentsRegisterSale(loader,stage);
            panelStage.initStyle(StageStyle.UNDECORATED);
            panelStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initComponentsRegisterSale(FXMLLoader loader,Stage stage){
        ProductoDAO productoDAO = new ProductoDAO();
        registerSaleController registerSaleController = null;
        try {
            registerSaleController = loader.getController();
            registerSaleController.setStage(stage);
            registerSaleController.initComponents(productoDAO.getAllProducts());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void changeViewSales(javafx.scene.input.MouseEvent mouseEvent) {
        btnVentas.setStyle("-fx-background-color: #523814; -fx-text-fill: #ffffff;");
        btnInicio.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnProducts.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        btnReportes.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/history.fxml"));
            BorderPane borderPane = loader.load();
            borderHome.setCenter(borderPane.getCenter());
            borderHome.setBottom(null);
            initComponentsSales(loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void backChangeViewSales(MouseEvent mouseEvent) {
        btnVentas.setStyle("-fx-background-color: #c2a894; -fx-text-fill: #000000;");
    }


    public void initComponentsSales(FXMLLoader loader){
        VentaDAO ventaDAO = new VentaDAO();
        SalesController salesController = null;
        List<Venta> ventas = ventaDAO.getSales();
        try {
            salesController = loader.getController();
            for (Venta v : ventas) {
                salesController.setData(v);
            }
            salesController.manageSelection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void minimizeApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnMinimizar.getScene().getWindow();
        currentStage.setIconified(true);
    }

    public void closeApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnCerrar.getScene().getWindow();
        currentStage.close();
    }

    public void exitApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnCerrar.getScene().getWindow();
        currentStage.close();
        new App();
    }
}
