package paneles;

import logger.TPVLogger;
import productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

/**
 * Clase que representa el panel de añadir un producto nuevo, formado por la parte de informacion y la numerica para asignar la cantidad
 * @author Rafael Niñoles Parra
 */
public class PanelAnyadeProducto {
    private static final Logger LOGGER = TPVLogger.getLogger();
    private final JPanel panel;
    private final PanelLateral panelLateral;
    private final JLabel labelProducto;
    private final PanelIntroduceNumero panelIntroduceNumero;
    private Producto producto;

    /**
     * Devuelve el JPanel
     * @return JPanel con el contenido
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Devuelve el producto
     * @return Producto actual
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Panel compuesto por un panel con la informacion del producto y otro panel para seleccionar la cantidad de este
     * @param panelLateral Panel en el que se creara
     */
    public PanelAnyadeProducto(PanelLateral panelLateral) {
        this.panelLateral = panelLateral;
        this.panel = new JPanel(new GridLayout(0,1));
        this.panelIntroduceNumero = new PanelIntroduceNumero(this);
        this.producto = null;
        labelProducto = new JLabel("No hay ningun producto seleccionado");
        labelProducto.setForeground(new Color(0,39,96));
        labelProducto.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
        labelProducto.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelProducto);
        panel.add(panelIntroduceNumero.getPanel());
    }

    /**
     * Reinicia la cantidad del producto
     */
    public void reiniciaCantidad(){
        panelIntroduceNumero.reinicia();
    }

    /**
     * Cambia el producto seleccionado por uno nuevo
     * @param producto producto nuevo a cambiar
     */
    public void cambiaProducto(Producto producto){
        this.producto = producto;
        cambiaTextoProducto(producto);
    }

    private void cambiaTextoProducto(Producto producto) {
        labelProducto.setText(producto.getNombre()+" - "+producto.getPrecioDecimal()+"€/unidad");
    }

    /**
     * Guarda el producto seleccionado en la lista de productos de la compra
     */
    public void aceptar() {
        panelLateral.anyadeProductoALista(producto,panelIntroduceNumero.getCantidad());
        this.producto = null;
        labelProducto.setText("No hay ningun producto seleccionado");
        panelIntroduceNumero.reinicia();
    }
}
