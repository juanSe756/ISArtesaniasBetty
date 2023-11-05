package com.artesaniasbetty.controllers;

import com.artesaniasbetty.model.*;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class VentaController {
    public String recordSale(String desc, Timestamp fecha_regist_venta, int idUsuario, HashMap<Integer, Integer> productosVenta) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        ProductoController productoController = new ProductoController();
        try (em) {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, idUsuario);
            Venta venta = new Venta(desc, fecha_regist_venta, usuario);
            em.persist(venta);
            /*em.getTransaction().commit();
            em.getTransaction().begin();*/
            for (Integer producto : productosVenta.keySet()) {
                DetalleVenta detalleVenta = new DetalleVenta(em.find(Producto.class, producto),venta, productosVenta.get(producto));
                productoController.decrementStock(producto, productosVenta.get(producto));
                em.persist(detalleVenta);
            }
            em.getTransaction().commit();
            em.close();
            return "Sale recorded";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error recording sale";
    }
}
