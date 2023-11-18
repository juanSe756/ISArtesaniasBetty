package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.Venta;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.List;

public class ReportsController {
    public BarChart barChart;
    public CategoryAxis axisX;
    public NumberAxis axisY;
    public Label infoIngresos;
    public Label infoNuevosProductos;
    public Label infoVentas;
    public Label infoCantidadProductos;

    public void init(List<Venta> ventaList){
        changeTypeList(ventaList);
        initChart();
        System.out.println(ventaList);
    }

    private void changeTypeList(List<Venta> ventaList) {
        for(Venta v : ventaList){

        }
    }

    public void initChart(){
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data<>("Categoría1", 10));
        series1.getData().add(new XYChart.Data<>("Categoría2", 20));

        barChart.getData().add(series1);
    }

    public void setInfo(){

    }
}
