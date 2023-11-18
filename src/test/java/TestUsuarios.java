import com.artesaniasbetty.dao.UsuarioDAO;

import java.sql.Timestamp;

public class TestUsuarios {
    public static void main(String[] args) {
    String prod = new UsuarioDAO().createUser("beatriz", "beatrizADM1N"
               , "Rita Beatriz", "Corredor", "+57 123 4567890", 1);
        System.out.println(prod);
    }
}
