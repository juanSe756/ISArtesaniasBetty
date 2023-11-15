package com.artesaniasbetty.dao;

import com.artesaniasbetty.model.*;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.*;

@Getter
public class VentaDAO {
    private HashMap<Integer, Integer> productosVenta;

    public List<Venta> getSales() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public String recordSale(String desc, int idUsuario, HashMap<Integer, Integer> productosVenta) {
        ProductoDAO productoController = new ProductoDAO();
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, idUsuario);
            Venta venta = new Venta(desc, new Timestamp(System.currentTimeMillis()), usuario);
            em.persist(venta);
            for (Integer producto : productosVenta.keySet()) {
                DetalleVenta detalleVenta = new DetalleVenta(em.find(Producto.class, producto), venta, productosVenta.get(producto));
                productoController.decrementStock(producto, productosVenta.get(producto));
                em.persist(detalleVenta);
            }
            em.getTransaction().commit();
            return "Sale recorded";
        } catch (Exception e) {
            return "Error recording sale";
        }
    }

    public double getTotalCostByBill(List<StringBuilder> bill) {
        double total = 0;
        for (StringBuilder stringBuilder : bill) {
            total += Double.parseDouble(stringBuilder.substring(stringBuilder.lastIndexOf(" ") + 1));
        }
        return total;
    }
//    Obtener las ventas hechas este mes
//    2592000000L = 30 dias en milisegundos
    public List<Venta> getSalesThisMonth() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.createQuery("SELECT v FROM Venta v WHERE v.fechaRegistroVenta BETWEEN :start AND :end", Venta.class)
                    .setParameter("start", new Timestamp(System.currentTimeMillis() - 2592000000L))
                    .setParameter("end", new Timestamp(System.currentTimeMillis()))
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
//    Obtener los reabastecimientos hechos este mes
//    2592000000L = 30 dias en milisegundos
    public List<DetalleVenta> getReabastecimientosThisMonth() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.createQuery("SELECT dv FROM DetalleVenta dv WHERE dv.venta.fechaRegistroVenta BETWEEN :start AND :end AND dv.cantidad < 0", DetalleVenta.class)
                    .setParameter("start", new Timestamp(System.currentTimeMillis() - 2592000000L))
                    .setParameter("end", new Timestamp(System.currentTimeMillis()))
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
//    Obtener la cantidad de productos creados en el ultimo mes
//    2592000000L = 30 dias en milisegundos
    public List<Producto> getProductsCreatedThisMonth() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.createQuery("SELECT p FROM Producto p WHERE p.fechaRegistroProducto BETWEEN :start AND :end", Producto.class)
                    .setParameter("start", new Timestamp(System.currentTimeMillis() - 2592000000L))
                    .setParameter("end", new Timestamp(System.currentTimeMillis()))
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public List<StringBuilder> getBill() {
        List<StringBuilder> bill = new ArrayList<>();
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
//            cant, nombre, precio, subtotal separados por espacios
            for (Integer producto : productosVenta.keySet()) {
                Producto p = em.find(Producto.class, producto);
                bill.add(new StringBuilder().append(productosVenta.get(producto)).append(":::").append(p.getNombre()).append(":::").append(p.getPrecio()).append(":::").append(p.getPrecio() * productosVenta.get(producto)));
            }
            return bill;
        } catch (Exception e) {
            return null;
        }
    }
}
