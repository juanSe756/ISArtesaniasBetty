package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.Venta;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportsController {
    public BarChart barChart;
    public CategoryAxis axisX;
    public NumberAxis axisY;
    public Label infoIngresos;
    public Label infoNuevosProductos;
    public Label infoVentas;
    public Label infoCantidadProductos;

    public void init(HashMap<String,Integer> top5Products){
        initChart(top5Products);
    }

    public void setData(double income, int newsProducts, int numberSales, int amountProducts){
        infoIngresos.setText(""+ NumberFormat.getCurrencyInstance().format(income));
        infoNuevosProductos.setText(""+newsProducts);
        infoCantidadProductos.setText(""+amountProducts);
        infoVentas.setText(""+numberSales);
    }
    public void initChart(HashMap<String, Integer> top5Products) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        int colorIndex = 0;
        for (Map.Entry<String, Integer> entry : top5Products.entrySet()) {
            String productName = entry.getKey();
            int quantitySold = entry.getValue();
            XYChart.Data<String, Number> data = new XYChart.Data<>(productName, quantitySold);
            series.getData().add(data);
            colorIndex++;
        }
        barChart.getData().add(series);
        aplicarEstilos(barChart);
    }

    private void aplicarEstilos(BarChart<String, Number> barChart) {
        int colorIndex = 0;
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                data.getNode().applyCss();
                String color = obtenerColorPorIndice(colorIndex);
                data.getNode().setStyle("-fx-bar-fill: " + color + ";");
                colorIndex++;
            }
        }
    }

    private String obtenerColorPorIndice(int indice) {
        String[] colores = {"#721817", "#fa9f42", "#2b4162", "#0b6e4f", "#f6bdd1"};
        return colores[indice % colores.length];
    }

    public void setInfo(){

    }
}
