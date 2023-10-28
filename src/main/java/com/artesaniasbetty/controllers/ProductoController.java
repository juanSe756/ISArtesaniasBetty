package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.Categoria;
import com.artesaniasbetty.model.Producto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ProductoController {
    public String createProduct(String nombre, double precio, String desc, int stock, Categoria categ){
        SessionFactory sf = new Configuration().configure("META-INF/hibernate.cfg.xml")
                .addAnnotatedClass(Producto.class).buildSessionFactory();
        Session session = sf.openSession();
        try {
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setDesc(desc);
            producto.setStock(stock);
            producto.setCategoria(categ);
            System.out.println(producto);
            session.beginTransaction();
            session.save(producto);
            session.getTransaction().commit();
            session.close();
            return "Producto created";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "error al crear el user";
    }
}
