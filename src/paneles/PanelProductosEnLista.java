package paneles;

import logger.LogFactory;
import productos.Producto;
import utilidades.estilos.UtilidadesEstilos;

import javax.security.auth.Destroyable;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.logging.Logger;

public class PanelProductosEnLista {
    private final static Logger LOGGER = LogFactory.getLogger(PanelProductosEnLista.class.getName());
    private final JPanel panel;
    private final Producto producto;
    private int cantidad;
    private final JButton botonEliminar;
    private final JLabel labelNombre;
    private final PanelListaCompra panelListaCompra;
    private final GridBagConstraints constraints;

    public PanelProductosEnLista(Producto producto, int cantidad, PanelListaCompra panelListaCompra) {
        this.panelListaCompra = panelListaCompra;
        this.producto = producto;
        this.cantidad = cantidad;
        this.botonEliminar = new JButton("X");
        botonEliminar.setHorizontalAlignment(SwingConstants.RIGHT);
        UtilidadesEstilos.botonCerrar(botonEliminar);
        this.labelNombre = new JLabel("");
        this.labelNombre.setHorizontalAlignment(SwingConstants.LEFT);
        this.labelNombre.setForeground(Color.BLACK);
        this.labelNombre.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,22));;
        actualizaLabelNombre();
        this.botonEliminar.addActionListener(e->{
            panelListaCompra.eliminaProducto(this);
        });
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

    private void actualizaLabelNombre() {
        this.labelNombre.setText(producto.getNombre()+" "+producto.getPrecioDecimal()+"€ x "+cantidad+" | Subtotal: "+String.format("%.2f",(double) (producto.getPrecioCentimos()*cantidad) / 100)+"€");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanelProductosEnLista that = (PanelProductosEnLista) o;
        return Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto);
    }

    public JPanel getPanel() {
        return panel;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void anyadeCantidad(int cantidad){
        this.cantidad += cantidad;
        actualizaLabelNombre();
    }
}
