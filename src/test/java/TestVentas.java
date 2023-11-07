import com.artesaniasbetty.controllers.VentaController;
import com.artesaniasbetty.model.Producto;

import java.sql.Timestamp;
import java.util.HashMap;

public class TestVentas {
    public static void main(String[] args) {
        HashMap<Integer, Integer> productos = new HashMap<>();
        productos.put(1, 5);
        productos.put(2, 5);
        productos.put(4, 5);
        String sale = new VentaController().recordSale("Venta de prueba3", new Timestamp(System.currentTimeMillis()), 8, productos);
        System.out.println(sale);
    }
}
