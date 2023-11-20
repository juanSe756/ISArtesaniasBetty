package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.model.Producto;
import com.artesaniasbetty.model.Venta;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import javax.swing.text.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainProductController {
    public Button btnNewProduct;
    public TableView<ProductTable> productsTable;
    @Getter
    public TableColumn<ProductTable,Integer> idColumn;
    public TableColumn<ProductTable,String> nameColumn;
    public TableColumn<ProductTable,Double> priceColumn;
    public TableColumn<ProductTable,String> descriptionColum;
    public TableColumn<ProductTable,Integer> stockColumn;
    public TableColumn<ProductTable,String> categoryColumn;
    public TableColumn colButtonDelete;
    public TableColumn colButtonModify;
    private ObservableList<ProductTable> products = FXCollections.observableArrayList();
    ProductoDAO productoDAO = new ProductoDAO();

    public ProductTable toProductTable(StringBuilder producto) {
        ProductTable productTable = null;
        String[] datosArray = producto.toString().split(":::");
        if (datosArray.length >= 6) {
            try {
                int id = Integer.parseInt(datosArray[0]);
                String name = datosArray[1];
                double price = Double.parseDouble(datosArray[2]);
                String description = datosArray[3];
                int stock = Integer.parseInt(datosArray[4]);
                String category = datosArray[5];

                productTable = new ProductTable(id, name,price, description,stock,
                        category);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Datos incompletos");
        }
        return productTable;
    }

    public void setData(StringBuilder sProducto) {
        ProductTable producto = toProductTable(sProducto);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionColum.setCellValueFactory(new PropertyValueFactory<>("description"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        products.add(producto);
        productsTable.setItems(products);
        productsTable.refresh();
    }

    public void changeViewNewProduct(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/newProduct.fxml"));
        BorderPane panel = null;
        try {
            panel = loader.load();
            Scene scene = new Scene(panel, 500, 550);

            // Obtén la ventana actual y muestra el nuevo panel en una ventana emergente
            Scene currentScene = btnNewProduct.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            Stage panelStage = new Stage();
            panelStage.setScene(scene);
            panelStage.initModality(Modality.APPLICATION_MODAL); // Esto hará que la nueva ventana sea modal
            panelStage.initOwner(stage); // Establece la ventana principal como propietaria de la nueva ventana
            initComponentsNewProduct(loader,stage);
            panelStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initComponentsNewProduct(FXMLLoader loader,Stage stage){
        NewProductController newProductController = null;
        //List<ProductoDAO> ventas = productoDAO;
        try {
            newProductController = loader.getController();
            newProductController.setStage(stage);
            newProductController.initComponents();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        // ... (otras configuraciones)

        // Configuración de la columna de botones de "Eliminar"
        colButtonDelete.setCellFactory(param -> new TableCell<ProductTable, String>() {
            private final Button deleteButton = new Button();
            private final ImageView deleteIcon =
                    new ImageView(new Image(getClass().getResourceAsStream("/assets/trash.png")));

            {
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setOnAction(event -> {
                    ProductTable producto = getTableView().getItems().get(getIndex());
                    boolean selected = showAlert(producto.getName());
                    if(selected){
                        productoDAO.removeProduct(producto.getId());
                        products.remove(producto);
                        showAlertInfo("Prodcuto eliminado exitosamente");
                    }else {
                        showAlertInfo("Proceso cancelado");
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Configuración de la columna de botones de "Modificar"
        colButtonModify.setCellFactory(param -> new TableCell<ProductTable, String>() {
            private final Button modifyButton = new Button();
            private final ImageView modifyIcon = new ImageView(new Image(getClass().getResourceAsStream("/assets" +
                    "/pencil.png")));

            {
                modifyButton.setGraphic(modifyIcon);
                modifyButton.setOnAction(event -> {
                    ProductTable producto = getTableView().getItems().get(getIndex());
                    // Manejar la acción de modificación aquí
                    // Puedes abrir un diálogo de edición u otras acciones
                    System.out.println("Modificar producto: " + producto.getName());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                }
            }
        });

        // ... (otras configuraciones)

        // Asignar la ObservableList a la TableView
        productsTable.setItems(products);
    }

    private boolean showAlert(String nameProduct) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacion");
        alert.setContentText("¿Deseas realmente eliminar el producto "+ nameProduct+ "?" );
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

    private void mostrarAlertError(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Error en la aplicacion");
        alert.showAndWait();
    }

    @FXML
    private void showAlertInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

