import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.model.DetalleVenta;
import com.artesaniasbetty.model.ReStock;
import com.artesaniasbetty.model.Venta;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class TestVentas {
    public static void main(String[] args) {
//        HashMap<Integer, Integer> productos = new HashMap<>();
//        productos.put(10, 2);
//        productos.put(3, 5);
//        productos.put(6, 3);
//        String sale = new VentaDAO().recordSale("Venta a cliente frecuente", 1, productos);
//        List<DetalleVenta> x = new VentaDAO().getDetalleFromVenta(2);
        List<Venta> x = new VentaDAO().getSalesThisMonth();
//        System.out.println(x);
        for (Venta venta : x) {
            System.out.println(venta.toString());
        }
    }
}
