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
    @Column(name = "id_prod")
    private int id;
    @Column(name = "nom_prod")
    private String nombre;
    @Column(name = "precio")
    private double precio;
    @Column(name = "descrip_prod")
    private String desc;
    @Column(name = "stock")
    private int stock;
    @Column(name = "categoria")
    private Categoria categoria;
}
