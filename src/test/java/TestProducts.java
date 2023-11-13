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
        String absolutePath = "C:\\Users\\USUARIO PC\\Documents\\UPTC\\2023-2\\zzzoftware\\prods\\canasto tipo jarra.jpg";
            String prod = new ProductoDAO().createProduct("Canasto tipo jarra", 20000,"Canasto tipo jarra tejido en fique, con asas y tapa."
                ,8, 2,absolutePath);
        //String prod = new ProductoController().removeProduct(4);
        //String prod = new ProductoController().decrementStock(1, 1);
//        String prod = new ProductoController().incrementStock( 2, 10,"reabastecimiento de canastoxd",2, new Timestamp(System.currentTimeMillis()));
//        Producto prod = new ProductoController().searchProduct(1);
//          String prod = new ProductoController().modifyProduct(1, "pofa", 2000,"o",2, 2,"https://image.com");
//        List<StringBuilder> x= new ProductoDAO().getProductsTable();
//        for (StringBuilder stringBuilder : x) {
//            System.out.println(stringBuilder);
//        }
        System.out.println(prod);
    }
}
