package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.Categoria;
import com.artesaniasbetty.model.Producto;
import com.artesaniasbetty.model.ReStock;
import com.artesaniasbetty.model.Usuario;
import jakarta.persistence.*;

import java.sql.Timestamp;


public class ProductoController {

    public String createProduct(String nombre, double precio, String desc, int stock, int categ, String foto) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        try (em) {
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, categ);
            Producto producto = new Producto(nombre, precio, desc, stock, categoria, foto);
            em.persist(producto);
            em.getTransaction().commit();
            em.close();
            return "Product created";
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            if(producto.getStock() > cantidad){
                producto.setStock(producto.getStock() - cantidad);
                em.persist(producto);
                em.getTransaction().commit();;
            }
            em.close();
            return "decremented stock";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error decrementing stock";
    }
    public String incrementStock(int id, int cantidad,String desc, int idUsuario, Timestamp fecha_reabast) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        ReStock reStock;
        try (em) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            reStock= new ReStock(desc, producto, cantidad, fecha_reabast, em.find(Usuario.class, idUsuario));
            em.persist(reStock);
            producto.setStock(producto.getStock() + cantidad);
            em.persist(producto);
            em.getTransaction().commit();
            em.close();
            return "incremented stock";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error incrementing stock";
    }
    public String modifyProduct(int id, String nombre, double precio, String desc, int stock, int categoria, String foto) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            Categoria categ = em.find(Categoria.class, categoria);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setDesc(desc);
            producto.setStock(stock);
            producto.setCategoria(categ);
            producto.setFoto(foto);
            em.getTransaction().commit();
            em.close();
            return "product modified";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error modifying product";
    }
    public String removeProduct(int id) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            em.remove(producto);
            em.getTransaction().commit();
            em.close();
            return "product removed";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error removing product";
    }
    public Producto searchProduct(int id) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        return em.find(Producto.class, id);
    }
    public Producto searchProduct(String name){
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM Producto p WHERE p.nombre = :name");
        query.setParameter("name", name);
        return (Producto) query.getSingleResult();
    }
}
