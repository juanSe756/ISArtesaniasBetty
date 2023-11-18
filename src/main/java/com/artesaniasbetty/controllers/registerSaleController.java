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
    private List<RegisterSaleTable> ventas;
    private List<Producto> productosVendadidos;

    private ObservableList<RegisterSaleTable> products = FXCollections.observableArrayList();

    public void initComponents(List<Producto> productos){
        ventas = new ArrayList<>();
        ObservableList<String> productosList = FXCollections.observableArrayList(toListText(productos));
        scrollProductos.setItems(productosList);
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
        RegisterSaleTable registerSaleTable =
                new RegisterSaleTable(Integer.parseInt(spinnerCantidad.getValue().toString()),
                producto.getNombre(),
                producto.getPrecio());
        ventas.add(registerSaleTable);
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPrecioTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        products.add(registerSaleTable);
        tablaVentas.setItems(products);
        tablaVentas.refresh();
        txtPrecio.setText("$"+calculateTotalPrice());
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

    public void generateSale(ActionEvent actionEvent) {
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        if(ventas.size() > 0) {
            for (RegisterSaleTable venta : ventas) {
                hashMap.put(ProductoDAO.searchProduct(venta.getName()).getId(), venta.getAmount());
            }
            createSale(areaDescripcion.getText(), 1, hashMap);
        }
    }
}
