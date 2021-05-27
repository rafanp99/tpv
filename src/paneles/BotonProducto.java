package paneles;

import logger.LogFactory;
import productos.Producto;
import programa.TPVCopisteria;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Logger;

public class BotonProducto {
    private final PanelProductos panelProductos;
    private static final Logger LOGGER = LogFactory.getLogger(BotonProducto.class.getName());
    private final JButton boton;
    private final Producto producto;

    public JButton getBoton() {
        return boton;
    }

    public BotonProducto(Producto producto,PanelProductos panelProductos) {
        this.producto = producto;
        this.panelProductos = panelProductos;
        this.boton = new JButton("");
        this.boton.addActionListener(e->{
            this.panelProductos.getPanelLateral().seleccionaProducto(producto);
        });
        disenyaBoton();
    }

    private void disenyaBoton() {
        try {
            ImageIcon imagen = new ImageIcon("resources/"+producto.getUriImagen());
            boton.setIcon(imagen);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning("No se ha encontrado la imagen del producto "+producto.getNombre());
        }
        boton.setBorder(BorderUIResource.getBlackLineBorderUIResource());
    }
}
