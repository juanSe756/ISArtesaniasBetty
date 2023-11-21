package com.artesaniasbetty.controllers;

import com.artesaniasbetty.App;
import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ResupplyController{
    public ComboBox comboProductos;
    public Spinner spinnerCantidad;
    public TextArea areaDescripcion;
    public Button btnCancelResupply;
    public Button btnConfirmResupply;
    public Button btnMinimize;
    public Button btnClose;
    ProductoDAO productoDAO =  new ProductoDAO();

    public void initComponents(List<Producto> productos){
        ObservableList<String> productosList = FXCollections.observableArrayList(toListText(productos));
        comboProductos.setItems(productosList);
        comboProductos.setValue(productosList.get(0));
        initSpinner();
    }

    public List<String> toListText(List<Producto> productos){
        List<String> productosList = new ArrayList<>();
        for (Producto producto : productos) {
            productosList.add(producto.getNombre());
        }
        return productosList;
    }

    private void initSpinner(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1); // Valores mínimos, máximos y por defecto

        spinnerCantidad.setValueFactory(valueFactory);
    }

    public void cancelSupply(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnCancelResupply.getScene().getWindow();
        currentStage.close();
    }

    public void updateSupply(ActionEvent actionEvent) {
        String name = (String) comboProductos.getValue();
        int amount = (int) spinnerCantidad.getValue();
        String description = areaDescripcion.getText();
        productoDAO.incrementStock(productoDAO.searchProduct(name).getId(),amount,description,1);
        changeViewErrors(new ActionEvent(),false,"Producto abastecido exitosamente");
        closeApp(new ActionEvent());
    }

    private void showAlertInfo(ActionEvent event,String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void minimizeApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnMinimize.getScene().getWindow();
        currentStage.setIconified(true);
    }

    public void closeApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    public void changeViewErrors(ActionEvent actionEvent, boolean type, String message) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/Errores.fxml"));
        BorderPane panel = null;
        try {
            panel = loader.load();

            // Obtén la ventana actual y muestra el nuevo panel en una ventana emergente
            Scene currentScene = btnConfirmResupply.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            // Crea la escena de la ventana de errores con las dimensiones de la ventana principal
            Scene scene = new Scene(panel, 350, 200);

            Stage panelStage = new Stage();
            panelStage.setScene(scene);
            panelStage.initModality(Modality.APPLICATION_MODAL);
            panelStage.initOwner(stage);
            initComponentsErrores(loader, stage, type, message);
            panelStage.initStyle(StageStyle.UNDECORATED);
            panelStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void initComponentsErrores(FXMLLoader loader,Stage stage,boolean type, String message){
        ErrorController errorController = null;
        try {
            errorController = loader.getController();
            errorController.setData(type,message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
