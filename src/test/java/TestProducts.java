import com.artesaniasbetty.controllers.ProductoController;
import com.artesaniasbetty.model.Producto;

import java.sql.Timestamp;

public class TestProducts {
    public static void main(String[] args) {
        String absolutePath = "C:\\Users\\USUARIO PC\\Documents\\UPTC\\2023-2\\zzzoftware\\canasto_osito.jpg";
        String prod = new ProductoController().createProduct("Canasto de osito", 45000,"Canasto personalizado con diseño de osito"
                ,18, 2,absolutePath);
        //String prod = new ProductoController().removeProduct(4);
        //String prod = new ProductoController().decrementStock(1, 1);
//        String prod = new ProductoController().incrementStock( 2, 10,"reabastecimiento de canastoxd",2, new Timestamp(System.currentTimeMillis()));
//        Producto prod = new ProductoController().searchProduct(1);
//          String prod = new ProductoController().modifyProduct(1, "pofa", 2000,"o",2, 2,"https://image.com");
        System.out.println(prod);
    }
}
