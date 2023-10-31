package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.Categoria;
import com.artesaniasbetty.model.Producto;
import jakarta.persistence.*;


public class ProductoController {

    public String createProduct(String nombre, double precio, String desc, int stock, int categ) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        try (em) {
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, categ);
            Producto producto = new Producto(nombre, precio, desc, stock, categoria);
            em.persist(producto);
            em.getTransaction().commit();
            em.close();
            return "Product created";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error creating product";
    }

    public String decrementStock(int id, int cantidad) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        try (em) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            producto.setStock(producto.getStock() - cantidad);
            em.persist(producto);
            em.getTransaction().commit();
            em.close();
            return "decremented stock";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error decrementing stock";
    }
    public String incrementStock(int id, int cantidad) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        try (em) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            producto.setStock(producto.getStock() + cantidad);
            em.persist(producto);
            em.getTransaction().commit();
            em.close();
            return "incremented stock";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error incrementing stock";
    }

}
