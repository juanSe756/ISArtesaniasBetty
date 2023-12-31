package com.artesaniasbetty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "VENTAS")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private int id;
    @Column(name = "descrip_venta")
    private String desc;
    @Column(name = "fecha_regist_venta")
    private Timestamp fechaRegistroVenta;
    @Column(name = "total_venta")
    private double totalVenta;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;
    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalleVentas;
    public Venta(String desc, Timestamp fechaRegistroVenta, Usuario idUsuario, double totalVenta) {
        this.desc = desc;
        this.fechaRegistroVenta = fechaRegistroVenta;
        this.idUsuario = idUsuario;
        this.totalVenta = totalVenta;
    }
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", fechaRegistroVenta=" + fechaRegistroVenta +
                ", idUsuario=" + idUsuario +
                ", totalVenta=" + totalVenta +
                '}';
    }
}
