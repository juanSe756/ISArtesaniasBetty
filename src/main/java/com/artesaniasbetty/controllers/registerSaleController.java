package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.dao.UsuarioDAO;
import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class registerSaleController {


    public ComboBox scrollProductos;
    public Spinner spinnerCantidad;
    public Button btnAgregarVenta;
    public TableView<RegisterSaleTable> tablaVentas;
    public TextArea areaDescripcion;
    public Label txtPrecio;
    public TableColumn<RegisterSaleTable,Integer> colCantidad;
    public TableColumn<RegisterSaleTable,String> colNombre;
    public TableColumn<RegisterSaleTable,Double> colPrecioUnitario;
    public TableColumn<RegisterSaleTable,Double> colPrecioTotal;
    public Button btnConfirmar;
    public Button btnCancelar;
    public Button btnMinimize;
    public Button btnClose;
    private List<RegisterSaleTable> ventas;
    private List<Producto> productosVendadidos;
    @Getter
    private Stage stage;

    private ObservableList<RegisterSaleTable> products = FXCollections.observableArrayList();

    public void initComponents(List<Producto> productos){
        ventas = new ArrayList<>();
        ObservableList<String> productosList = FXCollections.observableArrayList(toListText(productos));
        scrollProductos.setItems(productosList);
        scrollProductos.setValue(productosList.get(0));
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

    public void addSaleTable(ActionEvent actionEvent) {
        ProductoDAO productoDAO = new ProductoDAO();
        Producto producto = productoDAO.searchProduct(scrollProductos.getSelectionModel().getSelectedItem().toString());
        VentaDAO ventaDAO = new VentaDAO();
        if(producto.getStock() >= Integer.parseInt(spinnerCantidad.getValue().toString())) {
            RegisterSaleTable registerSaleTable =
                    new RegisterSaleTable(Integer.parseInt(spinnerCantidad.getValue().toString()),
                            producto.getNombre(),
                            producto.getPrecio());
            ventas.add(registerSaleTable);
            double totalPrice = calculateTotalPrice();
            colCantidad.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
            colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colPrecioTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            products.add(registerSaleTable);
            tablaVentas.setItems(products);
            tablaVentas.refresh();
            txtPrecio.setText("$" + totalPrice);
        }else {
            changeViewErrors(new ActionEvent(), true,"No hay suficiente stock de este producto");
        }
    }

    private double calculateTotalPrice(){
        double totalPrice = 0;
        for (RegisterSaleTable venta :ventas) {
            totalPrice += venta.getTotalPrice();
        }
        return totalPrice;
    }

    private void createSale(String desc, int idUser, HashMap<Integer,Integer> hashMap){
        new VentaDAO().recordSale(desc, idUser, hashMap);
    }

    private void showAlertError(ActionEvent actionEvent,String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void generateSale(ActionEvent actionEvent) {
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        ProductoDAO productoDAO = new ProductoDAO();
        if(ventas.size() > 0) {
            for (RegisterSaleTable venta : ventas) {
                hashMap.put(productoDAO.searchProduct(venta.getName()).getId(), venta.getAmount());
            }
            createSale(areaDescripcion.getText(), 1, hashMap);
            changeViewErrors(new ActionEvent(),false,"Venta registrada correctamente");
            Stage currentStage = (Stage) btnClose.getScene().getWindow();
            currentStage.close();
        }else {
            changeViewErrors(new ActionEvent(),true,"Venta vacía");
        }
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    private void showAlertSuccess(ActionEvent event,String message) {
        stage.requestFocus();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void cancelOperation(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnCancelar.getScene().getWindow();
        currentStage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
            Scene currentScene = btnCancelar.getScene();
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
