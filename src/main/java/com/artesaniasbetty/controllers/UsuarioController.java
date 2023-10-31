package com.artesaniasbetty.controllers;
import com.artesaniasbetty.model.*;
import jakarta.persistence.*;

import java.sql.Timestamp;
import org.mindrot.jbcrypt.BCrypt;
public class UsuarioController {
    public Estado searchEstado(String edo, EntityManager em){
        String jpql = "SELECT e FROM Estado e WHERE e.nombre = :nombre";
        return em.createQuery(jpql, Estado.class)
                .setParameter("nombre", edo)
                .getSingleResult();
    }
    public Rol searchRol(String rol, EntityManager em){
        String jpql = "SELECT r FROM Rol r WHERE r.nombre = :nombre";
        return em.createQuery(jpql, Rol.class)
                .setParameter("nombre", rol)
                .getSingleResult();
    }
    public String createUser(String nickname, String contrasena, String nombreUsuario,
                             String apellidoUsuario, String telefono, String estadoUsuario, Timestamp fechaRegistroUsuario, String rol){
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("persistence-betty");
        EntityManager em = emf.createEntityManager();
        Estado estado = searchEstado(estadoUsuario, em);
        Rol roLl = searchRol(rol, em);
        String pass = BCrypt.hashpw(contrasena, BCrypt.gensalt());

        try {
            Usuario usuario= new Usuario(nickname, pass, nombreUsuario,
                    apellidoUsuario, telefono, estado, fechaRegistroUsuario, roLl);
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            em.close();
            return "User created";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "Error creating user";
    }
    public boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
