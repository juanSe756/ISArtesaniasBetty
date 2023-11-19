package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.model.DetalleVenta;
import com.artesaniasbetty.model.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SalesController {
    public TableView<SaleTable> salesTable;
    public TableColumn<SaleTable, Integer> IdVentaColumn;
    public TableColumn<SaleTable, Date> fechaColumn;
    public TableColumn<SaleTable, String> descriptionColumn;
    public TableColumn<SaleTable, Double> ventaTotalColumn;
    public TableView<DetailSaleTable> detailSaleTable;
    public TableColumn<DetailSaleTable,Integer> colCantidad;
    public TableColumn<DetailSaleTable,String> colProducto;
    public TableColumn<DetailSaleTable,Double> colPrecioUnitario;
    public TableColumn<DetailSaleTable,Double> colPrecioTotal;
    private ObservableList<SaleTable> sales = FXCollections.observableArrayList();
    private ObservableList<DetailSaleTable> details = FXCollections.observableArrayList();

    public void setData(Venta venta) {
        detailSaleTable.setPlaceholder(new javafx.scene.control.Label("Seleccione una venta para ver el detalle"));
        SaleTable saleTable = toSaleTable(venta);
        IdVentaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        ventaTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalSale"));
        sales.add(saleTable);
        salesTable.setItems(sales);
        salesTable.refresh();
    }

    public void manageSelection() {
        // Configurar el escuchador para manejar la selección en la tabla
        salesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                details.clear();
                fillDetailTable(newValue.getId());
            }
        });
    }

    private SaleTable toSaleTable(Venta venta) {
        return new SaleTable(venta.getId(), venta.getFechaRegistroVenta(), venta.getDesc(), venta.getTotalVenta());
    }

    private void fillDetailTable(int idSale){
        VentaDAO ventaDAO = new VentaDAO();
        List<DetalleVenta> detalleVentas = ventaDAO.getDetalleFromVenta(idSale);
        for (DetalleVenta detalleVenta : detalleVentas) {
        updateDetail(toDetailTable(detalleVenta));
        }
    }

    private DetailSaleTable toDetailTable(DetalleVenta detailSale){
        return new DetailSaleTable(detailSale.getCantidad(),detailSale.getProducto().getNombre(),
                detailSale.getProducto().getPrecio());
    }

    public void updateDetail(DetailSaleTable detailTable){
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPrecioTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        details.add(detailTable);
        detailSaleTable.setItems(details);
        detailSaleTable.refresh();
    }
}
