package com.artesaniasbetty.controllers;

import com.artesaniasbetty.App;
import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    ProductoDAO productoDAO =  new ProductoDAO();

    public void initComponents(List<Producto> productos){
        ObservableList<String> productosList = FXCollections.observableArrayList(toListText(productos));
        comboProductos.setItems(productosList);
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Prodecimiento cancelado");
        alert.setTitle("Info");
        alert.setContentText("No se reabateció el producto");
        alert.showAndWait();
    }

    public void updateSupply(ActionEvent actionEvent) {

        String name = (String) comboProductos.getValue();
        int amount = (int) spinnerCantidad.getValue();
        String description = areaDescripcion.getText();
        //App.usuarioLogeado.getId(
        productoDAO.incrementStock(productoDAO.searchProduct(name).getId(),amount,description,1);
    }
}
