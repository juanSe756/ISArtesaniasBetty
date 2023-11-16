package com.artesaniasbetty.dao;

import com.artesaniasbetty.model.*;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.*;

@Getter
public class VentaDAO {
    private HashMap<Integer, Integer> productosVenta = new HashMap<>();

    public String recordSale(String desc, int idUsuario, HashMap<Integer, Integer> productosVenta) {
        ProductoDAO productoController = new ProductoDAO();
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, idUsuario);
            Venta venta = new Venta(desc, new Timestamp(System.currentTimeMillis()), usuario, getCostOfProducts(productosVenta));
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

    public List<Venta> getSales() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public double getIncomeThisMonth() {
        double income = 0;
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            List<Venta> ventas = em.createQuery("SELECT v FROM Venta v WHERE v.fechaRegistroVenta BETWEEN :start AND :end", Venta.class)
                    .setParameter("start", new Timestamp(System.currentTimeMillis() - 2592000000L))
                    .setParameter("end", new Timestamp(System.currentTimeMillis()))
                    .getResultList();
            for (Venta venta : ventas) {
                income += venta.getTotalVenta();
            }
            return income;
        } catch (Exception e) {
            return 0;
        }
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

    public List<DetalleVenta> getDetalleFromVenta(int idVenta) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            Venta venta = em.find(Venta.class, idVenta);
            return em.createQuery("SELECT dv FROM DetalleVenta dv WHERE dv.venta = :idVenta", DetalleVenta.class)
                    .setParameter("idVenta", venta)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    //    multiplicar la clave por el valor de un hashmap integer, integer
    public double getCostOfProducts(HashMap<Integer, Integer> productosVenta) {
        double total = 0;
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            for (Integer producto : productosVenta.keySet()) {
                Producto prod = em.find(Producto.class, producto);
                total += prod.getPrecio() * productosVenta.get(producto);
            }
            return total;
        } catch (Exception e) {
            return 0;
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
