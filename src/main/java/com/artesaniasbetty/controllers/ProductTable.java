package com.artesaniasbetty.controllers;

import javafx.scene.control.Button;
import lombok.Getter;

import java.awt.*;

@Getter
public class ProductTable {
    private int id;
    private String name;
    private double price;
    private String description;
    private int stock;
    private String category;
    @Getter
    private Button customButton1;
    @Getter
    private Button customButton2;
    public ProductTable(int id, String name, double price, String description, int stock, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.category = category;
    }

    public void setCustomButton1(Button customButton1) {
        this.customButton1 = customButton1;
    }

    public void setCustomButton2(Button customButton2) {
        this.customButton2 = customButton2;
    }

    @Override
    public String toString() {
        return "ProductTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                '}';
    }
}
