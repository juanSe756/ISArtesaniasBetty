import com.artesaniasbetty.dao.EntityMF;
import com.artesaniasbetty.dao.ProductoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestProducts {
    public static void main(String[] args) {
        String absolutePath = "C:\\Users\\USUARIO PC\\Documents\\UPTC\\2023-2\\zzzoftware\\canasto_osito.jpg";
//        String prod = new ProductoDAO().createProduct("Canasta de osito", 15000,"Canasta personalizada con diseño de oso"
//                ,15, 1,absolutePath);
        //String prod = new ProductoController().removeProduct(4);
        //String prod = new ProductoController().decrementStock(1, 1);
//        String prod = new ProductoController().incrementStock( 2, 10,"reabastecimiento de canastoxd",2, new Timestamp(System.currentTimeMillis()));
//        Producto prod = new ProductoController().searchProduct(1);
//          String prod = new ProductoController().modifyProduct(1, "pofa", 2000,"o",2, 2,"https://image.com");
        List<StringBuilder> x= new ProductoDAO().getProductsTable();
        for (StringBuilder stringBuilder : x) {
            System.out.println(stringBuilder);
        }
    }
}
