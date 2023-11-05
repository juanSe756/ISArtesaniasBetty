import com.artesaniasbetty.controllers.VentaController;
import com.artesaniasbetty.model.Producto;

import java.sql.Timestamp;
import java.util.HashMap;

public class TestVentas {
    public static void main(String[] args) {
        HashMap<Integer, Integer> productos = new HashMap<>();
        productos.put(1, 1);
        productos.put(2, 2);
        String sale = new VentaController().recordSale("Venta de prueba", new Timestamp(System.currentTimeMillis()), 1, productos);
        System.out.println(sale);
    }
}
