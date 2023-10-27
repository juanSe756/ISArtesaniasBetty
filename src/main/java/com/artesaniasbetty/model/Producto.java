package com.artesaniasbetty.model;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio")
    private double precio;
    @Column(name = "cant_disp")
    private int cant_disp;
    @Column(name = "desc")
    private String desc;

}
