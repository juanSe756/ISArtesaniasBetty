package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.Categoria;
import com.artesaniasbetty.model.Producto;
import jakarta.persistence.*;




public class ProductoController {
    public Categoria searchCategoria(String categ, EntityManager em){
        String jpql = "SELECT c FROM Categoria c WHERE c.nombre = :nombre";
        return em.createQuery(jpql, Categoria.class)
                .setParameter("nombre", categ)
                .getSingleResult();
    }
    public String createProduct(String nombre, double precio, String desc, int stock, String categ){
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        Categoria categoria = searchCategoria(categ, em);

        try {
            Producto producto = new Producto(nombre, precio, desc, stock, categoria);
            producto.setCategoria(categoria);
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            em.close();
            return "Producto created";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "error al crear el user";
    }
}
