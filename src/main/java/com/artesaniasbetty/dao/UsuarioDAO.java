package com.artesaniasbetty.dao;

import com.artesaniasbetty.model.*;
import jakarta.persistence.*;

import java.sql.Timestamp;

import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {
    public String createUser(String nickname, String contrasena, String nombreUsuario,
                             String apellidoUsuario, String telefono, int rol) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            String pass = BCrypt.hashpw(contrasena, BCrypt.gensalt());
            Estado estado = em.find(Estado.class, 1);
            Rol roLl = em.find(Rol.class, rol);
            em.getTransaction().begin();
            Usuario usuario = new Usuario(nickname, pass, nombreUsuario,
                    apellidoUsuario, telefono, estado, new Timestamp(System.currentTimeMillis()), roLl);
            em.persist(usuario);
            em.getTransaction().commit();
            return "Usuario creado exitosamente";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error creando usuario";
    }

    public String removeUser(int id) {
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Usuario user = em.find(Usuario.class, id);
            user.setEstadoUsuario(em.find(Estado.class, 2));
            em.persist(user);
            em.getTransaction().commit();
            return "Usuario eliminado exitosamente";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error eliminando usuario";
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
            return "Usuario modificado exitosamente";
        } catch (Exception e) {
            return "Error modificando usuario";
        }
    }

    public boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
