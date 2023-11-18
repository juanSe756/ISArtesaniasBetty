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
//        productos.put(11, 4);
//        productos.put(2, 2);
//        productos.put(8, 6);
//        String sale = new VentaDAO().recordSale("Venta de valor para la empresa", 1, productos);
//        List<DetalleVenta> x = new VentaDAO().getDetalleFromVenta(2);
//        List<Venta> x = new VentaDAO().getSalesThisMonth();
        double x = new VentaDAO().getIncomeThisMonth();
//        double x = new VentaDAO().getCostOfProducts(productos);
        System.out.println(x);
//        for (Venta venta : x) {
//            System.out.println(venta.toString());
//        }
    }
}