package com.artesaniasbetty.dao;

import com.artesaniasbetty.model.*;
import jakarta.persistence.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ProductoDAO {
    private static final int IMG_SIZE = 200;

    public void createProduct(String nombre, double precio, String desc, int stock, int categ, String fotoURL) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            saveImage(fotoURL, nombre);
            byte[] foto = convertImageToBytes(fotoURL);
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, categ);
            Estado estado = em.find(Estado.class, 1);
            Producto producto = new Producto(nombre, precio, desc, stock, categoria, estado, foto, new Timestamp(System.currentTimeMillis()));
            em.persist(producto);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<StringBuilder> getProductsTable() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            TypedQuery<Object[]> query = em.createQuery("SELECT p.id, p.nombre, p.precio, p.desc, p.stock, p.categoria.nombre FROM Producto p WHERE p.estadoProducto = :activo", Object[].class)
                    .setParameter("activo", em.find(Estado.class, 1));
            List<Object[]> resultList = query.getResultList();
            List<StringBuilder> resultStrings = new ArrayList<>();
            for (Object[] result : resultList) {
                resultStrings.add(new StringBuilder().append(result[0]).append(":::").append(result[1]).append(":::").append(result[2]).append(":::").append(result[3]).append(":::").append(result[4]).append(":::").append(result[5]));
            }
            return resultStrings;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ReStock> getReStocksThisMonth() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.createQuery("SELECT r FROM ReStock r WHERE r.fecha BETWEEN :start AND :end AND r.cantidad > 0", ReStock.class)
                    .setParameter("start", new Timestamp(System.currentTimeMillis() - 2592000000L))
                    .setParameter("end", new Timestamp(System.currentTimeMillis()))
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<ReStock> getReStocks() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.createQuery("SELECT r FROM ReStock r WHERE r.cantidad > 0", ReStock.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    //    Obtener la cantidad de productos creados en el ultimo mes
//    2592000000L = 30 dias en milisegundos
    public int getProductsCreatedThisMonth() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.createQuery("SELECT p FROM Producto p WHERE p.fechaRegistroProducto BETWEEN :start AND :end", Producto.class)
                    .setParameter("start", new Timestamp(System.currentTimeMillis() - 2592000000L))
                    .setParameter("end", new Timestamp(System.currentTimeMillis()))
                    .getResultList().size();
        } catch (Exception e) {
            return 0;
        }
    }

    private byte[] convertImageToBytes(String imagePath) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(imagePath));
        BufferedImage resizedImage = resize(originalImage);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        return baos.toByteArray();
    }

    private BufferedImage resize(BufferedImage img) {
        Image tmp = img.getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(IMG_SIZE, IMG_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    private void convertBytesToImage(byte[] imageBytes, String productName) {
        try {
            String outputPath = "src/main/resources/assets/prods/" + productName + ".jpg";
            FileOutputStream fos = new FileOutputStream(outputPath);
            fos.write(imageBytes);
            fos.close();
        } catch (IOException e) {
            System.out.println("Couldn't convert bytes to image, check the path");
        }
    }

    //guarda la imagen en la carpeta assets/prods apartir de una ruta dada por parametro
    public void saveImage(String imagePath, String productName) {
        try {
            byte[] imageBytes = convertImageToBytes(imagePath);
            convertBytesToImage(imageBytes, productName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void decrementStock(int id, int cantidad, EntityManager em) {
        Producto producto = em.find(Producto.class, id);
        producto.setStock(producto.getStock() - cantidad);
        em.persist(producto);
//        em.getTransaction().commit();
    }

    public String incrementStock(int id, int cantidad, String desc, int idUsuario) {
        ReStock reStock;
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            reStock = new ReStock(desc, producto, cantidad, new Timestamp(System.currentTimeMillis()), em.find(Usuario.class, idUsuario));
            em.persist(reStock);
            producto.setStock(producto.getStock() + cantidad);
            em.persist(producto);
            em.getTransaction().commit();
            em.close();
            return "Producto reabastecido exitosamente";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error reabasteciendo producto";
    }

    public String modifyProduct(int id, String nombre, double precio, String desc, int stock, int categoria, String
            fotoURL) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            saveImage(fotoURL, nombre);
            byte[] foto = convertImageToBytes(fotoURL);
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
            return "Producto modificado exitosamente";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error modificando producto";
    }

    public String removeProduct(int id) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            producto.setEstadoProducto(em.find(Estado.class, 2));
            em.persist(producto);
            em.getTransaction().commit();
            return "Producto eliminado exitosamente";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error eliminando producto";
    }

    //    metodo para traer todos los productos en una lista
    public List<Producto> getAllProducts() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE p.estadoProducto = :activo", Producto.class);
            query.setParameter("activo", em.find(Estado.class, 1));
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Producto searchProduct(int id) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            return em.find(Producto.class, id);
        } catch (NoResultException e) {
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
