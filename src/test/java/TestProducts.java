import com.artesaniasbetty.dao.EntityMF;
import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.model.ReStock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestProducts {
    public static void main(String[] args) {
//        String absolutePath = "C:\Users\USUARIO PC\Documents\UPTC\2023-2\zzzoftware\prods\canasto tipo jarra 15k.jpg";
//        new ProductoDAO().createProduct("Canasto de Jarra", 15000,"Canasto con diseño de jarra"
//                ,15, 1,absolutePath);
        //String prod = new ProductoController().removeProduct(4);
        //String prod = new ProductoController().decrementStock(1, 1);
//        ProductoDAO productoDAO = new ProductoDAO();
//        productoDAO.incrementStock( 2, 15,"Quedaba poco inventario de este producto",4);
//        productoDAO.incrementStock( 3, 15,"Quedaba poco inventario de este producto",4);

//        Producto prod = new ProductoController().searchProduct(1);
//          String prod = new ProductoController().modifyProduct(1, "pofa", 2000,"o",2, 2,"https://image.com");
        List<StringBuilder> x= new ProductoDAO().getProductsTable();
        for (StringBuilder stringBuilder : x) {
            System.out.println(stringBuilder);
        }
//        HashMap<String,byte[]> x= new ProductoDAO().getProductsImage();
//        for (String producto : x.keySet()) {
//            System.out.println(producto + "---" + Arrays.toString(x.get(producto)));
//        }
//        List<ReStock> x = new ProductoDAO().getReStocks();
////        System.out.println(x);
//        for (ReStock venta : x) {
//            System.out.println(venta.toString());
//        }
//        System.out.println(prod);
    }
}
