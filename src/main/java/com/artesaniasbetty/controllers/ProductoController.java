package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.Categoria;
import com.artesaniasbetty.model.Producto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;


public class ProductoController {
    public Categoria searchCategoria(String categ, EntityManager em){
        String jpql = "SELECT c FROM Categoria c WHERE c.nombre = :nombre";
        return em.createQuery(jpql, Categoria.class)
                .setParameter("nombre", categ)
                .getSingleResult();
    }
    public String createProduct(String nombre, double precio, String desc, int stock, String categ){
        SessionFactory sf = new Configuration().configure("META-INF/hibernate.cfg.xml")
                .addAnnotatedClass(Producto.class).buildSessionFactory();
        Session session = sf.openSession();
        EntityManager em = (EntityManager) sf.createEntityManager();
        Categoria categoria = searchCategoria(categ, em);

        try {
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setDesc(desc);
            producto.setStock(stock);
            producto.setCategoria(categoria);
            session.beginTransaction();
            session.save(producto);
            session.getTransaction().commit();
            em.close();
            return "Producto created";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "error al crear el user";
    }
}
