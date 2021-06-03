package tiquets;

import paneles.PanelProductosEnLista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Tiquet implements Serializable {
    private final List<PanelProductosEnLista> productos;
    private int totalEnCent;
    private int cantidadProductos;
    private final LocalDateTime fechaActual;

    public List<PanelProductosEnLista> getProductos() {
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

    public Tiquet(List<PanelProductosEnLista> productos) {
        this.productos = new ArrayList<>(productos);
        this.fechaActual = LocalDateTime.now();
        calculaCantidadYTotal();
    }

    private void calculaCantidadYTotal() {
        for (PanelProductosEnLista producto:productos) {
            totalEnCent+=(producto.getCantidad()*producto.getProducto().getPrecioCentimos());
            cantidadProductos+=producto.getCantidad();
        }
    }
}
