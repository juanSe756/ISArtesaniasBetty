package com.artesaniasbetty.controllers;
import com.artesaniasbetty.model.*;
import jakarta.persistence.*;

import java.sql.Timestamp;
import org.mindrot.jbcrypt.BCrypt;
public class UsuarioController {
    public String createUser(String nickname, String contrasena, String nombreUsuario,
                             String apellidoUsuario, String telefono, int estadoUsuario, Timestamp fechaRegistroUsuario, int rol){
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            String pass = BCrypt.hashpw(contrasena, BCrypt.gensalt());
            Estado estado = em.find(Estado.class, estadoUsuario);
            Rol roLl = em.find(Rol.class, rol);
            em.getTransaction().begin();
            Usuario usuario = new Usuario(nickname, pass, nombreUsuario,
                    apellidoUsuario, telefono, estado, fechaRegistroUsuario, roLl);
            em.persist(usuario);
            em.getTransaction().commit();
            return "User created";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error creating user";
    }
    public String removeUser(int id) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Usuario user = em.find(Usuario.class, id);
            em.remove(user);
            em.getTransaction().commit();
            return "user removed";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error removing user";
    }
    public String modifyUser(int id, String contrasena, String nombreUsuario,
                             String apellidoUsuario, String telefono, int estadoUsuario) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            Estado estado = em.find(Estado.class, estadoUsuario);
            String pass = BCrypt.hashpw(contrasena, BCrypt.gensalt());
            usuario.setContrasena(pass);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setApellidoUsuario(apellidoUsuario);
            usuario.setTelefono(telefono);
            usuario.setEstadoUsuario(estado);
            em.getTransaction().commit();
            return "user modified";
        } catch (Exception e) {
            return "Error modifying user";
        }
    }
    public boolean verifyPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
