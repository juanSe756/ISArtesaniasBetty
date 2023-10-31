package com.artesaniasbetty.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categ")
    private int id;
    @Column(name = "nom_categ")
    private String nombre;
    @Column(name = "descrip_categ")
    private String desc;
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}

