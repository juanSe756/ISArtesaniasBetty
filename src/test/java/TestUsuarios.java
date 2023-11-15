import com.artesaniasbetty.dao.UsuarioDAO;

import java.sql.Timestamp;

public class TestUsuarios {
    public static void main(String[] args) {
    String prod = new UsuarioDAO().createUser("sebas", "sebas756"
               , "Sebastian", "Gil", "+57 3106021341", 1, 3);
        //String prod = new UsuarioController().removeUser(3);
        /*String prod = new UsuarioController().modifyUser(1, "holaxd123"
                , "Sebastian", "Gil", "3183092020", 1, "https://image.com");*/
        System.out.println(prod);
    }
}
