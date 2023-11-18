package com.artesaniasbetty.controllers;

import lombok.Getter;

@Getter
public class RegisterSaleTable {
    private int amount;
    private String name;
    private double unitPrice;
    private double totalPrice;

    public RegisterSaleTable(int amount, String name, double unitPrice) {
        this.amount = amount;
        this.name = name;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice*amount;
    }
}
