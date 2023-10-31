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
@Table(name = "estados")
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
}
