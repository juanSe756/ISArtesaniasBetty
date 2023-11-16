import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.model.DetalleVenta;
import com.artesaniasbetty.model.ReStock;
import com.artesaniasbetty.model.Venta;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class TestVentas {
    public static void main(String[] args) {
        HashMap<Integer, Integer> productos = new HashMap<>();
        productos.put(1, 2);
        productos.put(2, 2);
        productos.put(3, 2);
        String sale = new VentaDAO().recordSale("Venta comun de algunos productos", 1, productos);
//        List<DetalleVenta> x = new VentaDAO().getDetalleFromVenta(2);
//        List<Venta> x = new VentaDAO().getSalesThisMonth();
//        double x = new VentaDAO().getIncomeThisMonth();
//        double x = new VentaDAO().getCostOfProducts(productos);
        System.out.println(sale);
//        for (Venta venta : x) {
//            System.out.println(venta.toString());
//        }
    }
}
