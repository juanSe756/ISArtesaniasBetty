import com.artesaniasbetty.dao.ProductoDAO;

import java.util.List;

public class TestProducts {
    public static void main(String[] args) {
//        String absolutePath = "C:\\Users\\USUARIO PC\\Documents\\UPTC\\2023-2\\zzzoftware\\prods\\canasto tipo jarra 15k.jpg";
//            String prod = new ProductoDAO().createProduct("Canasto de Jarra", 15000,"Canasto con diseño de jarra"
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
//        List<ReStock> x = new ProductoDAO().getReStocks();
////        System.out.println(x);
//        for (ReStock venta : x) {
//            System.out.println(venta.toString());
//        }
//        System.out.println(prod);
    }
}
