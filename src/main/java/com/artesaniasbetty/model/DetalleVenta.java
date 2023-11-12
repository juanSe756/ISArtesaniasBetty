package com.artesaniasbetty.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DETALLES_VENTA")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_venta")
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_prod")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;
    @Column(name = "cant_vendida")
    private int cantidad;
//    builder
    public DetalleVenta(Producto producto, Venta venta, int cantidad) {
        this.producto = producto;
        this.venta = venta;
        this.cantidad = cantidad;
    }
}
