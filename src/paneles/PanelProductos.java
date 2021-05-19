package paneles;

import productos.ListaProductos;
import productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class PanelProductos {
    private final JPanel panel;
    private final Set<Producto> listaProductos;

    public PanelProductos(ListaProductos listaProductos) {
        this.panel = new JPanel(new GridLayout(0,5));
        this.listaProductos = listaProductos.getProductos();
        anyadeBotonesProductos();
    }

    private void anyadeBotonesProductos() {
        for (Producto producto:listaProductos) {
        }
    }

    public HashSet<Producto> getListaProductos() {
        return new HashSet<>(listaProductos);
    }

}
