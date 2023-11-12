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
import java.util.List;


public class ProductoDAO {
    private static final int IMG_SIZE = 80;
    public String createProduct(String nombre, double precio, String desc, int stock, int categ, String fotoURL) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            byte[] foto=convertImageToBytes(fotoURL);
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, categ);
            Estado estado = em.find(Estado.class, 1);
            Producto producto = new Producto(nombre, precio, desc, stock, categoria, estado,foto);
            em.persist(producto);
            em.getTransaction().commit();
            return "Product created";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error creating product";
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
    public String modifyProduct(int id, String nombre, double precio, String desc, int stock, int categoria, String fotoURL) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            byte[] foto=convertImageToBytes(fotoURL);
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
//    metodo para traer todos los productos en una lista
    public List<Producto> getAllProducts() {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p", Producto.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
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
