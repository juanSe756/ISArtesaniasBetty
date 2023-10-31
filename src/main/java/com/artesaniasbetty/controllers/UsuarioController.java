package com.artesaniasbetty.controllers;
import com.artesaniasbetty.model.*;
import jakarta.persistence.*;

import java.sql.Timestamp;
import org.mindrot.jbcrypt.BCrypt;
public class UsuarioController {
    public String createUser(String nickname, String contrasena, String nombreUsuario,
                             String apellidoUsuario, String telefono, int estadoUsuario, Timestamp fechaRegistroUsuario, int rol){
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        try (EntityManager em = emf.createEntityManager()) {
            String pass = BCrypt.hashpw(contrasena, BCrypt.gensalt());
            Estado estado = em.find(Estado.class, estadoUsuario);
            Rol roLl = em.find(Rol.class, rol);
            em.getTransaction().begin();
            Usuario usuario = new Usuario(nickname, pass, nombreUsuario,
                    apellidoUsuario, telefono, estado, fechaRegistroUsuario, roLl);
            em.persist(usuario);
            em.getTransaction().commit();
            em.close();
            return "User created";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error creating user";
    }
    public String removeUser(int id) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        try (em) {
            em.getTransaction().begin();
            Usuario user = em.find(Usuario.class, id);
            em.remove(user);
            em.getTransaction().commit();
            em.close();
            return "user removed";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error removing user";
    }
    public boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
