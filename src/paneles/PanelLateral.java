package paneles;

import logger.LogFactory;
import productos.Producto;
import programa.TPVCopisteria;
import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

/**
 * Clase que representa el panel lateral completo del TPV
 * @author Rafael Niñoles Parra
 */
public class PanelLateral {
    private static final Logger LOGGER = LogFactory.getLogger();
    private final JPanel panel;
    private final PanelAnyadeProducto panelAnyadeProducto;
    private final PanelListaCompra panelListaCompra;
    private final TPVCopisteria programaPrincipal;
    private final PanelBotonPagar panelBotonPagar;
    private final GridBagConstraints constraint;
    private final VentanaCobrar ventanaCobrar;
    private final JButton botonCierreCaja;
    private final JButton botonExportarEstadisticas;

    /**
     * Devuelve el panel lateral completo
     * @return panel lateral completo
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Deuelve el panel del boton de pagar
     * @return Panel de boton de pagar
     */
    public PanelBotonPagar getPanelBotonPagar() {
        return panelBotonPagar;
    }

    /**
     * Crea un panel lateral completo
     * @param programaPrincipal Programa principal
     */
    public PanelLateral(TPVCopisteria programaPrincipal){
        this.constraint = new GridBagConstraints();
        this.programaPrincipal = programaPrincipal;
        this.panel = new JPanel(new GridBagLayout());
        this.botonCierreCaja = new JButton("CIERRE CAJA");
        this.botonExportarEstadisticas = new JButton("GENERAR ESTADISTICAS");
        this.panelListaCompra = new PanelListaCompra(this);
        this.ventanaCobrar = new VentanaCobrar(panelListaCompra);
        UtilidadesEstilos.botonAzulClaro(botonCierreCaja);
        UtilidadesEstilos.botonAzulClaro(botonExportarEstadisticas);
        anyadeListenersBotones();
        constraint.fill=GridBagConstraints.HORIZONTAL;
        constraint.anchor=GridBagConstraints.NORTH;
        constraint.gridx=0;
        constraint.gridy=0;
        constraint.weightx=1;
        constraint.gridwidth=1;
        this.panel.add(botonCierreCaja,constraint);
        constraint.gridx=1;
        this.panel.add(botonExportarEstadisticas,constraint);
        constraint.gridwidth=2;
        constraint.gridx=0;
        constraint.gridy=2;
        this.panelAnyadeProducto = new PanelAnyadeProducto(this);
        this.panel.add(panelAnyadeProducto.getPanel(),constraint);
        constraint.gridy=3;
        constraint.gridheight=1;
        this.panel.add(panelListaCompra.getPanel(),constraint);
        this.panelBotonPagar = new PanelBotonPagar(this);
        constraint.gridy=4;
        constraint.gridheight=10;
        this.panel.add(panelBotonPagar.getPanel(),constraint);
    }

    /**
     * Finaliza la compra actual
     */
    public void finalizaCompra() {
        ventanaCobrar.reiniciaCobro();
        ventanaCobrar.abrir();
    }

    /**
     * Selecciona un nuevo producto
     * @param producto producto nuevo seleccionado
     */
    public void seleccionaProducto(Producto producto){
        panelAnyadeProducto.cambiaProducto(producto);
        panelAnyadeProducto.reiniciaCantidad();
    }

    /**
     * Añade un nuevo producto a la lista de la compra
     * @param producto Producto a añadir
     * @param cantidad Cantidad de veces que se compra el producto
     */
    public void anyadeProductoALista(Producto producto, int cantidad){
        panelListaCompra.anyadeProducto(producto,cantidad);
        panelBotonPagar.anyadePrecio(producto.getPrecioCentimos() * cantidad);
    }

    private void anyadeListenersBotones() {
        botonCierreCaja.addActionListener(e-> TPVCopisteria.imprimeCierreCaja());
        botonExportarEstadisticas.addActionListener(e-> TPVCopisteria.generaEstadisticasTPV());
    }
}
