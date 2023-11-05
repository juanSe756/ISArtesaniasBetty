import com.artesaniasbetty.controllers.ProductoController;
import com.artesaniasbetty.model.Producto;

public class TestProducts {
    public static void main(String[] args) {
        //String prod = new ProductoController().createProduct("pofa", 30000,"ccc",24, 2);
        //String prod = new ProductoController().removeProduct(4);
        //String prod = new ProductoController().decrementStock(1, 1);
        //String prod = new ProductoController().incrementStock(1, 1);
//        Producto prod = new ProductoController().searchProduct("jdudd");
          String prod = new ProductoController().modifyProduct(1, "pofa", 2000,"o",2, 2,"https://image.com");
        System.out.println(prod);
    }
}
