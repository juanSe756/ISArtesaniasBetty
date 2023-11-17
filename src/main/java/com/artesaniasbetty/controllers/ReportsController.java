package com.artesaniasbetty.controllers;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ReportsController {
    public BarChart barChart;
    public CategoryAxis axisX;
    public NumberAxis axisY;

    public void init(){
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data<>("Categoría1", 10));
        series1.getData().add(new XYChart.Data<>("Categoría2", 20));

        barChart.getData().add(series1);
    }
}
