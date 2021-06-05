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

    public String getInforme() {
        String salida="";
        salida+=getFechaFormateada()+"\n";
        String columna1Formato="%-35.35s";
        String columna2Formato="%-9.9s";
        String columna3Formato="%-13.13s";
        String columna4Formato="%10.10s";
        salida+=String.format(columna1Formato,"Nombre producto");
        salida+=String.format(columna2Formato,"Cantidad");
        salida+=String.format(columna3Formato,"Precio unidad");
        salida+=String.format(columna4Formato,"SUBTOTAL")+"\n";
        salida+="--------------------------------------------------------------------\n";
        for (ProductoEnTiquet productoEnTiquet:productos) {
            salida+=String.format(columna1Formato,productoEnTiquet.getProducto().getNombre());
            salida+=String.format(columna2Formato,productoEnTiquet.getCantidad());
            salida+=String.format(columna3Formato,productoEnTiquet.getProducto().getPrecioDecimal()+"€");
            salida+=String.format(columna4Formato,String.format("%.2f",(double) productoEnTiquet.getCantidad()*(productoEnTiquet.getProducto().getPrecioCentimos()/100)))+"€\n";
        }
        salida+="--------------------------------------------------------------------\n";
        salida+="TOTAL: "+String.format("%.2f",(double)totalEnCent/100)+"€";
        return salida;
    }
}
