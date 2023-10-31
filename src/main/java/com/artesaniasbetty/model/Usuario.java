package com.artesaniasbetty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int id;
    @Column(name = "nickname_usuario")
    private String nickname;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Column(name = "apellido_usuario")
    private String apellidoUsuario;
    @Column(name = "telefono")
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estadoUsuario;
    @Column(name = "fecha_regist_usuario")
    private Timestamp fechaRegistroUsuario;
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
    public Usuario(String nickname, String contrasena, String nombreUsuario, String apellidoUsuario, String telefono, Estado estadoUsuario, Timestamp fechaRegistroUsuario, Rol rol) {
        this.nickname = nickname;
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.telefono = telefono;
        this.estadoUsuario = estadoUsuario;
        this.fechaRegistroUsuario = fechaRegistroUsuario;
        this.rol = rol;

    }
}
