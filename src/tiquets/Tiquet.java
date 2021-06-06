package tiquets;

import paneles.PanelProductosEnLista;
import productos.ProductoEnTiquet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Clase que un tiquet completo
 * @author Rafael Niñoles Parra
 */
public class Tiquet implements Serializable {
    private final List<ProductoEnTiquet> productos;
    private int totalEnCent;
    private int cantidadProductos;
    private final LocalDateTime fechaActual;

    /**
     * Devuelve la lista de productos en tiquet del tiquet
     * @return lista de productos en tiquet del tiquet
     */
    public List<ProductoEnTiquet> getProductos() {
        return productos;
    }

    /**
     * Devuelve el total del tiquet en centimos
     * @return total del tiquet en centimos
     */
    public int getTotalEnCent() {
        return totalEnCent;
    }

    /**
     * Devuelve la cantidad de productos del tiquet
     * @return cantidad de productos del tiquet
     */
    public int getCantidadProductos() {
        return cantidadProductos;
    }

    /**
     * Devuelve LocalDateTime de la fecha del tiquet
     * @return LocalDateTime de la fecha del tiquet
     */
    public LocalDateTime getFechaActual() {
        return fechaActual;
    }

    /**
     * Devuelve la fecha del tiquet formateada
     * @return fecha del tiquet formateada
     */
    public String getFechaFormateada(){
        String salida="";
        salida+=fechaActual.getDayOfMonth()+"/";
        salida+=fechaActual.getMonthValue()+"/";
        salida+=fechaActual.getYear()+" ";
        salida+=fechaActual.getHour()+":";
        salida+=fechaActual.getMinute()+":";
        salida+=fechaActual.getSecond();
        return salida;
    }

    /**
     * Crea un nuevo tiquet
     * @param productosEnListas productos que tendra el tiquet
     */
    public Tiquet(List<PanelProductosEnLista> productosEnListas){
        this.productos = new ArrayList<>();
        for (PanelProductosEnLista panelProducto:productosEnListas) {
            this.productos.add(panelProducto.getProductoEnTiquet());
        }
        this.fechaActual = LocalDateTime.now();
        calculaCantidadYTotal();
    }

    private void calculaCantidadYTotal() {
        for (ProductoEnTiquet producto:productos) {
            totalEnCent+=(producto.getCantidad()*producto.getProducto().getPrecioCentimos());
            cantidadProductos+=producto.getCantidad();
        }
    }

    /**
     * Devuelve el informe completo del tiquet
     * @return informe completo del tiquet
     */
    public String getInforme() {
        //Usado el StringBuilder porque se añadia demasiadas veces al string y usando el for se acababan creando muchos objetos
        StringBuilder salida= new StringBuilder();
        salida.append(getFechaFormateada()).append("\n");
        String columna1Formato="%-35.35s";
        String columna2Formato="%-9.9s";
        String columna3Formato="%-13.13s";
        String columna4Formato="%10.10s";
        salida.append(String.format(columna1Formato, "Nombre producto"));
        salida.append(String.format(columna2Formato, "Cantidad"));
        salida.append(String.format(columna3Formato, "Precio unidad"));
        salida.append(String.format(columna4Formato, "SUBTOTAL")).append("\n");
        salida.append("--------------------------------------------------------------------\n");
        for (ProductoEnTiquet productoEnTiquet:productos) {
            salida.append(String.format(columna1Formato, productoEnTiquet.getProducto().getNombre()));
            salida.append(String.format(columna2Formato, productoEnTiquet.getCantidad()));
            salida.append(String.format(columna3Formato, productoEnTiquet.getProducto().getPrecioDecimal() + "€"));
            salida.append(String.format(columna4Formato, String.format("%.2f", (double) productoEnTiquet.getCantidad() * (productoEnTiquet.getProducto().getPrecioCentimos() / 100)))).append("€\n");
        }
        salida.append("--------------------------------------------------------------------\n");
        salida.append("TOTAL: ").append(String.format("%.2f", (double) totalEnCent / 100)).append("€");
        return salida.toString();
    }
}
