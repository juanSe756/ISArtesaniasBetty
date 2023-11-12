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
    String prod = new UsuarioController().createUser("sebas", "sebas756"
               , "Sebastian", "Gil", "+57 3106021341", 1, new Timestamp(System.currentTimeMillis()), 2);
        //String prod = new UsuarioController().removeUser(3);
        /*String prod = new UsuarioController().modifyUser(1, "holaxd123"
                , "Sebastian", "Gil", "3183092020", 1, "https://image.com");*/
        System.out.println(prod);
    }
}
