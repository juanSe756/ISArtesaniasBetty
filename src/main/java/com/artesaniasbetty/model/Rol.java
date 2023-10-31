package com.artesaniasbetty.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private int id;
    @Column(name = "nom_rol")
    private String nombre;
    @Column(name = "descrip_rol")
    private String desc;
    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;
}
