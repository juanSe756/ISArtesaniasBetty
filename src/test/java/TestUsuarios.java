import com.artesaniasbetty.controllers.EntityMF;
import com.artesaniasbetty.controllers.UsuarioController;
import com.artesaniasbetty.model.Estado;
import com.artesaniasbetty.model.Rol;
import com.artesaniasbetty.model.Usuario;
import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;

public class TestUsuarios {
    public static void main(String[] args) {
//    String prod = new UsuarioController().createUser("sebas15", "sebas15"
//               , "Sebastian", "Gil", "+57 3106021341", 1, new Timestamp(System.currentTimeMillis()), 2);
//        //String prod = new UsuarioController().removeUser(3);
//        /*String prod = new UsuarioController().modifyUser(1, "holaxd123"
//                , "Sebastian", "Gil", "3183092020", 1, "https://image.com");*/
//        System.out.println(prod);
        String prod = new TestUsuarios().createRol("prueba", "Administrador de la pagina");
        System.out.println(prod);
    }
    public String createRol( String nombreRol, String descripcionRol){
        try (EntityManager em = EntityMF.getInstance().createEntityManager()) {
            em.getTransaction().begin();
            Rol rol = new Rol(nombreRol, descripcionRol);
            em.persist(rol);
            em.getTransaction().commit();
            return "rol created";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Error creating rol";
    }
}
