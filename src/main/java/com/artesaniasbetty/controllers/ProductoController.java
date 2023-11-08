package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.*;
import jakarta.persistence.*;

import java.sql.Timestamp;


public class ProductoController {

    public String createProduct(String nombre, double precio, String desc, int stock, int categ, String foto) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, categ);
            Estado estado = em.find(Estado.class, 1);
            Producto producto = new Producto(nombre, precio, desc, stock, categoria, estado,foto);
            em.persist(producto);
            em.getTransaction().commit();
            return "Product created";
        } catch (Exception e) {
            return "Error creating product";
        }
    }

    public String decrementStock(int id, int cantidad) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            if(producto.getStock() > cantidad){
                producto.setStock(producto.getStock() - cantidad);
                em.persist(producto);
                em.getTransaction().commit();
                return "decremented stock";
            }
            else{
                return "Not enough stock";
            }
        } catch (Exception e) {
            return "Error decrementing stock";
        }
    }
    public String incrementStock(int id, int cantidad,String desc, int idUsuario, Timestamp fecha_reabast) {
        ReStock reStock;
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
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
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
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
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            em.remove(producto);
            em.getTransaction().commit();
            return "product removed";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error removing product";
    }
    public Producto searchProduct(int id) {
        try(EntityManager em = EntityMF.getInstance().createEntityManager()){
            return em.find(Producto.class, id);
        }
        catch (NoResultException e){
            return null;
        }
    }
    public Producto searchProduct(String name) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE p.nombre = :name", Producto.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
