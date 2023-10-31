package com.artesaniasbetty.model;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne
    @JoinColumn(name = "id_categ")
    private Categoria categoria;
    public Producto(String nombre, double precio, String desc, int stock, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.desc = desc;
        this.stock = stock;
        this.categoria = categoria;
    }

    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", desc='" + desc + '\'' +
                ", stock=" + stock +
                ", categoria=" + categoria.toString() +
                '}';
    }
}
