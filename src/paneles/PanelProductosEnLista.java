package paneles;

import logger.TPVLogger;
import productos.Producto;
import productos.ProductoEnTiquet;
import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Clase que representa los productos añadidos al panel de la lista de la compra
 * @author Rafael Niñoles Parra
 */
public class PanelProductosEnLista {
    private final static Logger LOGGER = TPVLogger.getLogger();
    private final JPanel panel;
    private final ProductoEnTiquet productoEnTiquet;
    private final JButton botonEliminar;
    private final JLabel labelNombre;
    private final PanelListaCompra panelListaCompra;
    private final GridBagConstraints constraints;

    /**
     * Crea un nuevo producto en lista
     * @param producto Producto que contendrá
     * @param cantidad Cantidad del producto
     * @param panelListaCompra Referencia al panel de la lista de la compra en el que estará
     */
    public PanelProductosEnLista(Producto producto, int cantidad, PanelListaCompra panelListaCompra) {
        this.panelListaCompra = panelListaCompra;
        this.productoEnTiquet = new ProductoEnTiquet(producto,cantidad);
        this.botonEliminar = new JButton("X");
        botonEliminar.setHorizontalAlignment(SwingConstants.RIGHT);
        UtilidadesEstilos.botonCerrar(botonEliminar);
        this.labelNombre = new JLabel("");
        this.labelNombre.setHorizontalAlignment(SwingConstants.LEFT);
        this.labelNombre.setForeground(Color.BLACK);
        this.labelNombre.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
        actualizaLabelNombre();
        this.botonEliminar.addActionListener(e-> panelListaCompra.eliminaProducto(this));
        this.panel = new JPanel(new GridBagLayout());
        this.panel.setBorder(BorderFactory.createEmptyBorder(7,7,7,7));
        this.constraints = new GridBagConstraints();
        this.constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.weightx=1;
        constraints.gridwidth=8;
        this.panel.add(labelNombre,constraints);
        constraints.gridx=8;
        constraints.gridwidth=4;
        constraints.weightx=0;
        this.panel.add(botonEliminar,constraints);
    }

    /**
     * Devuelve el JPanel completo
     * @return JPanel completo
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Devuele el Producto en tiquet
     * @return producto en tiquet
     */
    public ProductoEnTiquet getProductoEnTiquet() {
        return productoEnTiquet;
    }

    /**
     * Añade cantidad al producto
     * @param cantidad a añadir al producto
     */
    public void anyadeCantidad(int cantidad){
        productoEnTiquet.anyadeCantidad(cantidad);
        actualizaLabelNombre();
    }

    private void actualizaLabelNombre() {
        this.labelNombre.setText(productoEnTiquet.getProducto().getNombre()+" "+productoEnTiquet.getProducto().getPrecioDecimal()+"€ x "+productoEnTiquet.getCantidad()+" | Subtotal: "+String.format("%.2f",(double) (productoEnTiquet.getProducto().getPrecioCentimos()*productoEnTiquet.getCantidad()) / 100)+"€");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanelProductosEnLista that = (PanelProductosEnLista) o;
        return Objects.equals(productoEnTiquet, that.getProductoEnTiquet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productoEnTiquet.getProducto());
    }

}
