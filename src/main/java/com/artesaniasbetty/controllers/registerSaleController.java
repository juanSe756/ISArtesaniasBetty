package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.dao.UsuarioDAO;
import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private List<RegisterSaleTable> ventas;
    private List<Producto> productosVendadidos;

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
            showAlertSuccess(new ActionEvent(),"No hay stock de este producto");
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
            showAlertSuccess(new ActionEvent(),"Venta registrada correctamente");
        }else {
            showAlertError(new ActionEvent(),"Venta vacía");
        }
    }

    private void showAlertSuccess(ActionEvent event,String message) {
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
}
