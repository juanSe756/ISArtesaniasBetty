import com.artesaniasbetty.dao.VentaDAO;
import com.artesaniasbetty.model.DetalleVenta;
import com.artesaniasbetty.model.Producto;
import com.artesaniasbetty.model.ReStock;
import com.artesaniasbetty.model.Venta;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class TestVentas {
    public static void main(String[] args) {
//        HashMap<Integer, Integer> productos = new HashMap<>();
//        productos.put(1, 2);
//        productos.put(17, 2);
//        productos.put(15, 15);
//        String sale = new VentaDAO().recordSale("Venta de canastos", 4, productos);
//        System.out.println(sale);
//        List<DetalleVenta> x = new VentaDAO().getDetalleFromVenta(2);
//        List<Venta> x = new VentaDAO().getSalesThisMonth();
        double x = new VentaDAO().getIncomeThisMonth();
        System.out.println(x);
//        HashMap<String, Integer> x = new VentaDAO().getTop5Products();
//        for (String producto : x.keySet()) {
//            System.out.println(producto + "---" + x.get(producto));
//        }
//        int x = new VentaDAO().getQuantitySold(1);
    }
}
