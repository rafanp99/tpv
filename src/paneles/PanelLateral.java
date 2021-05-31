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
    private final PanelBotonPagar panelBotonPagar;
    private final GridBagConstraints constraint;
    private final VentanaCobrar ventanaCobrar;

    public void seleccionaProducto(Producto producto){
        panelAnyadeProducto.cambiaProducto(producto);
        panelAnyadeProducto.reiniciaCantidad();
    }

    public JPanel getPanel() {
        return panel;
    }

    public PanelBotonPagar getPanelBotonPagar() {
        return panelBotonPagar;
    }

    public void anyadeProductoALista(Producto producto, int cantidad){
        panelListaCompra.anyadeProducto(producto,cantidad);
        panelBotonPagar.anyadePrecio(producto.getPrecioCentimos() * cantidad);
    }

    public PanelLateral(TPVCopisteria programaPrincipal){
        this.constraint = new GridBagConstraints();
        this.programaPrincipal = programaPrincipal;
        this.panel = new JPanel(new GridBagLayout());
        constraint.fill=GridBagConstraints.HORIZONTAL;
        constraint.anchor=GridBagConstraints.NORTH;
        constraint.gridx=0;
        constraint.gridy=0;
        this.panelAnyadeProducto = new PanelAnyadeProducto(this);
        this.panel.add(panelAnyadeProducto.getPanel(),constraint);
        this.panelListaCompra = new PanelListaCompra(this);
        constraint.gridy=1;
        constraint.gridheight=1;
        this.panelBotonPagar = new PanelBotonPagar(this);
        this.panel.add(panelBotonPagar.getPanel(),constraint);
        constraint.gridy=2;
        constraint.gridheight=10;
        this.panel.add(panelListaCompra.getPanel(),constraint);
        this.ventanaCobrar = new VentanaCobrar(panelListaCompra);
    }

    public void finalizaCompra() {
        ventanaCobrar.reiniciaCobro();
        ventanaCobrar.abrir();
    }
}
