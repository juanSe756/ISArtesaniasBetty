package com.artesaniasbetty.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ESTADOS")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private int id;
    @Column(name = "nom_estado")
    private String nombre;
    @Column(name = "descrip_estado")
    private String desc;
    @OneToMany(mappedBy = "estadoUsuario")
    private List<Usuario> usuarios;
    @OneToMany(mappedBy = "estadoProducto")
    private List<Producto> productos;
//    builder
    public Estado(String nombre, String desc) {
        this.nombre = nombre;
        this.desc = desc;
    }
    public String toString() {
        return "Estado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
