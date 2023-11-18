package com.artesaniasbetty.controllers;

import lombok.Getter;

@Getter
public class DetailSaleTable {

    private int amount;
    private String nameProduct;
    private double unitPrice;
    private double totalPrice;

    public DetailSaleTable(int amount, String nameProduct, double unitPrice) {
        this.amount = amount;
        this.nameProduct = nameProduct;
        this.unitPrice = unitPrice;
        this.totalPrice = amount*unitPrice;
    }

    public DetailSaleTable() {
    }

    @Override
    public String toString() {
        return "DetailSaleTable{" +
                "amount=" + amount +
                ", nameProduct='" + nameProduct + '\'' +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
