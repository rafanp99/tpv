package paneles;

import logger.TPVLogger;
import productos.Producto;
import utilidades.estilos.UtilidadesEstilos;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Clase que representa el boton asociado a un producto
 * @author Rafael Niñoles Parra
 */
public class BotonProducto {
    private final PanelProductos panelProductos;
    private static final Logger LOGGER = TPVLogger.getLogger();
    private final JButton boton;
    private final Producto producto;

    /**
     * Devuelve un JButton para el producto
     * @return JButton para el producto
     */
    public JButton getBoton() {
        return boton;
    }

    /**
     * Crea un boton asociado a un producto
     * @param producto El producto al que estara asociado el boton
     * @param panelProductos El panel en el que se encontrara el boton
     */
    public BotonProducto(Producto producto,PanelProductos panelProductos) {
        this.producto = producto;
        this.panelProductos = panelProductos;
        this.boton = new JButton(producto.getNombre());
        this.boton.addActionListener(e-> this.panelProductos.getPanelLateral().seleccionaProducto(producto));
        disenyaBoton();
    }

    private void disenyaBoton() {
        try {
            InputStream contenidoImg = new FileInputStream("./imagenesProductos/"+producto.getUriImagen());
            ImageIcon imagen = new ImageIcon(ImageIO.read(contenidoImg));
            boton.setIcon(imagen);
            boton.setText("");
            boton.setForeground(Color.BLACK);
        } catch (Exception e) {
            e.printStackTrace();
            UtilidadesEstilos.botonAzul(boton);
            LOGGER.warning("No se ha encontrado la imagen del producto "+producto.getNombre());
        }
        boton.setBackground(new Color(241, 250, 238));
        boton.setBorder(BorderUIResource.getBlackLineBorderUIResource());
    }
}
