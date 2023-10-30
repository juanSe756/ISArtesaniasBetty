package com.artesaniasbetty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_categ")
    private int id;
    @Column(name = "nom_categ")
    private String nombre;
    @Column(name = "descrip_categ")
    private String desc;
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}

