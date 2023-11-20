package com.artesaniasbetty.dao;

import com.artesaniasbetty.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.*;

@Getter
public class VentaDAO {

    private HashMap<Integer, Integer> productosVenta = new HashMap<>();

    public boolean addToHashmap(int idProducto, int cantidad) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            Producto producto = em.find(Producto.class, idProducto);
            if (producto.getStock() >= cantidad) {
                productosVenta.put(idProducto, cantidad);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public String recordSale(String desc, int idUsuario, HashMap<Integer, Integer> productosVenta) {
        ProductoDAO productoController = new ProductoDAO();
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
                Usuario usuario = em.find(Usuario.class, idUsuario);
                Venta venta = new Venta(desc, new Timestamp(System.currentTimeMillis()), usuario, getCostOfProducts(productosVenta));
                em.persist(venta);
                for (Integer producto : productosVenta.keySet()) {
                    DetalleVenta detalleVenta = new DetalleVenta(em.find(Producto.class, producto), venta, productosVenta.get(producto));
                    productoController.decrementStock(producto, productosVenta.get(producto), em);
                    em.persist(detalleVenta);
                }
                em.getTransaction().commit();
            return "Venta realizada exitosamente";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error realizando venta";
        }
    }

    public List<Venta> getSales() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Venta> cq = cb.createQuery(Venta.class);
            Root<Venta> root = cq.from(Venta.class);
            cq.select(root);
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public double getIncomeThisMonth() {
            try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
                Double income = em.createQuery(
                                "SELECT SUM(v.totalVenta) FROM Venta v " +
                                        "WHERE FUNCTION('MONTH', v.fechaRegistroVenta) = FUNCTION('MONTH', CURRENT_DATE) " +
                                        "AND FUNCTION('YEAR', v.fechaRegistroVenta) = FUNCTION('YEAR', CURRENT_DATE)", Double.class)
                        .getSingleResult();

                return income != null ? income : 0;
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

    //    obtener el nombre y la cantidad vendida de los 5 productos mas vendidos como un hashmap
    public HashMap<String, Integer> getTop5Products() {
        HashMap<String, Integer> top5 = new HashMap<>();
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            List<Object[]> resultList = em.createQuery(
                            "SELECT dv.producto, SUM(dv.cantidad) " +
                                    "FROM DetalleVenta dv " +
                                    "GROUP BY dv.producto ", Object[].class)
                    .setMaxResults(5)
                    .getResultList();

            for (Object[] result : resultList) {
                Producto producto = (Producto) result[0];
                Long cantidadVendida = (Long) result[1];
                top5.put(producto.getNombre(), cantidadVendida.intValue());
            }

            return top5;
        } catch (Exception e) {
            return null;
        }
    }

    public int getQuantitySoldByProduct(int idProducto) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            String jpql = "SELECT COALESCE(SUM(dv.cantidad), 0) " +
                    "FROM DetalleVenta dv " +
                    "JOIN dv.producto p " +
                    "WHERE p.id = :idProducto";

            Long cantidadVendida = em.createQuery(jpql, Long.class)
                    .setParameter("idProducto", idProducto)
                    .getSingleResult();

            return cantidadVendida.intValue();
        } catch (Exception e) {
            return 0;
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