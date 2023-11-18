package com.artesaniasbetty.controllers;

import lombok.Getter;

import java.sql.Timestamp;
@Getter
public class SaleTable {

    private  int id;
    private Timestamp date;
    private String description;
    private double totalSale;

    public SaleTable(int id, Timestamp date, String description, double totalSale) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.totalSale = totalSale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(double totalSale) {
        this.totalSale = totalSale;
    }

    @Override
    public String toString() {
        return "SaleTable{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", totalSale=" + totalSale +
                '}';
    }
}
