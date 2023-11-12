package com.artesaniasbetty.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "REABASTECIMIENTOS")
public class ReStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reabast")
    private int id;
    @Column(name = "descrip_reabast")
    private String desc;
    @ManyToOne
    @JoinColumn(name = "id_prod")
    private Producto productoReStock;
    @Column(name = "cant_reabast")
    private int cantidad;
    @Column(name = "fecha_reabast")
    private Timestamp fecha;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuarioReStock;
//    builder
    public ReStock(String desc, Producto productoReStock, int cantidad, Timestamp fecha, Usuario usuarioReStock) {
        this.desc = desc;
        this.productoReStock = productoReStock;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.usuarioReStock = usuarioReStock;
    }
}
