package paneles;

import logger.LogFactory;
import productos.Producto;

import javax.swing.*;
import java.util.logging.Logger;

public class BotonProducto {
    private final PanelProductos panelProductos;
    private static final Logger LOGGER = LogFactory.getLogger(BotonProducto.class.getName());
    private final JButton boton;
    private Producto producto;

    public JButton getBoton() {
        return boton;
    }

    public BotonProducto(Producto producto,PanelProductos panelProductos) {
        this.producto = producto;
        this.panelProductos = panelProductos;
        this.boton = new JButton(producto.getNombre());
        disenyaBoton();
    }

    private void disenyaBoton() {
        //TODO Dar diseño al boton
    }
}
