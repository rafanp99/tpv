package tiquets;

import paneles.PanelProductosEnLista;
import productos.ProductoEnTiquet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Tiquet implements Serializable {
    private final List<ProductoEnTiquet> productos;
    private int totalEnCent;
    private int cantidadProductos;
    private final LocalDateTime fechaActual;

    public List<ProductoEnTiquet> getProductos() {
        return productos;
    }

    public int getTotalEnCent() {
        return totalEnCent;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public LocalDateTime getFechaActual() {
        return fechaActual;
    }

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
}
