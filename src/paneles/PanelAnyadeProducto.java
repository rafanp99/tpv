package paneles;

import logger.LogFactory;
import productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.logging.Logger;

public class PanelAnyadeProducto {
    private static final Logger LOGGER = LogFactory.getLogger(PanelAnyadeProducto.class.getName());
    private final JPanel panel;
    private final PanelLateral panelLateral;
    private final JLabel labelProducto;
    private Producto producto;
    private int cantidad;
    private PanelIntroduceNumero panelIntroduceNumero;

    public JPanel getPanel() {
        return panel;
    }

    public Producto getProducto() {
        return producto;
    }

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
    public void reiniciaCantidad(){
        panelIntroduceNumero.reinicia();
    }

    public void cambiaProducto(Producto producto){
        this.producto = producto;
        cambiaTextoProducto(producto);
    }
    private void cambiaTextoProducto(Producto producto) {
        labelProducto.setText(producto.getNombre()+" - "+producto.getPrecioDecimal()+"€/unidad");
    }

    public void aceptar() {
        panelLateral.anyadeProductoALista(producto,panelIntroduceNumero.getCantidad());
        this.producto = null;
        labelProducto.setText("No hay ningun producto seleccionado");
        panelIntroduceNumero.reinicia();
    }
}
