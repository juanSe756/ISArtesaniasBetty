import com.artesaniasbetty.controllers.UsuarioController;

import java.sql.Timestamp;

public class TestUsuarios {
    public static void main(String[] args) {
//        String prod = new UsuarioController().createUser("juanse756god", "ffsfksdf8"
//                , "Sebas", "Gil", "187434737", 1, new Timestamp(System.currentTimeMillis()), 3, "https://image.com");
        //String prod = new UsuarioController().removeUser(3);
        String prod = new UsuarioController().modifyUser(1, "holaxd123"
                , "Sebastian", "Gil", "3183092020", 1, "https://image.com");
        System.out.println(prod);
    }
}
