package paneles;

import logger.LogFactory;
import productos.Producto;
import programa.TPVCopisteria;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class PanelLateral {
    private static final Logger LOGGER = LogFactory.getLogger(PanelLateral.class.getName());
    private final JPanel panel;
    private final PanelAnyadeProducto panelAnyadeProducto;
    private final PanelListaCompra panelListaCompra;
    private final TPVCopisteria programaPrincipal;

    public void seleccionaProducto(Producto producto){
        panelAnyadeProducto.cambiaProducto(producto);
        panelAnyadeProducto.reiniciaCantidad();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void anyadeProductoALista(Producto producto, int cantidad){
        panelListaCompra.anyadeProducto(producto,cantidad);
    }

    public PanelLateral(TPVCopisteria programaPrincipal){
        this.programaPrincipal = programaPrincipal;
        this.panel = new JPanel(new GridLayout(0,1));
        this.panelAnyadeProducto = new PanelAnyadeProducto(this);
        this.panel.add(panelAnyadeProducto.getPanel());
        this.panelListaCompra = new PanelListaCompra(this);
        this.panel.add(panelListaCompra.getPanel());
    }
}
