package com.artesaniasbetty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario")
    private int id;
    @Column(name = "nickname_usuario")
    private String nickname;
    @Column(name = "contrasena")
    private double contrasena;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Column(name = "apellido_usuario")
    private String apellidoUsuario;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "estado_usuario")
    private Estado estadoUsuario;
    @Column(name = "fecha_regist_usuario")
    private Date fechaRegistroUsuario;
    @Column(name = "id_rol")
    private Rol rol;
}
