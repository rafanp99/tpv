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

    public PanelProductosEnLista(Producto producto, int cantidad, PanelListaCompra panelListaCompra) {
        this.panelListaCompra = panelListaCompra;
        this.producto = producto;
        this.cantidad = cantidad;
        this.panel = new JPanel(new GridLayout(0,2));
        this.panel.setBackground(Color.BLUE);
        this.panel.setForeground(Color.WHITE);
        this.botonEliminar = new JButton("X");
        UtilidadesEstilos.botonAzul(botonEliminar);
        this.labelNombre = new JLabel("");
        this.labelNombre.setForeground(Color.WHITE);
        this.labelNombre.setHorizontalAlignment(SwingConstants.CENTER);
        this.labelNombre.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,22));;
        actualizaLabelNombre();
        this.botonEliminar.addActionListener(e->{
            panelListaCompra.eliminaProducto(this);
        });
        this.panel.add(labelNombre);
        this.panel.add(botonEliminar);
    }

    private void actualizaLabelNombre() {
        this.labelNombre.setText("x"+cantidad+" "+producto.getNombre()+" | Subtotal: "+String.format("%.2f",(double) (producto.getPrecioCentimos()*cantidad) / 100));
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
