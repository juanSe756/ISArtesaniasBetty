package com.artesaniasbetty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_venta")
    private int id;
    @Column(name = "descrip_venta")
    private String desc;
    @Column(name = "fecha_regist_venta")
    private Date fechaRegistroVenta;
    @Column(name = "id_usuario")
    private int idUsuario;
}
