import com.artesaniasbetty.controllers.UsuarioController;

import java.sql.Timestamp;

public class TestUsuarios {
    public static void main(String[] args) {
    String prod = new UsuarioController().createUser("sebas7778", "hola123"
               , "Sebastian", "Gil", "187434737", 1, new Timestamp(System.currentTimeMillis()), 2);
        //String prod = new UsuarioController().removeUser(3);
        /*String prod = new UsuarioController().modifyUser(1, "holaxd123"
                , "Sebastian", "Gil", "3183092020", 1, "https://image.com");*/
        System.out.println(prod);
    }
}
