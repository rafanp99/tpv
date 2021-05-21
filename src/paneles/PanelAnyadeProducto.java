package paneles;

import logger.LogFactory;
import productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class PanelAnyadeProducto implements Introducible {
    private static final Logger LOGGER = LogFactory.getLogger(PanelAnyadeProducto.class.getName());
    private final JPanel panel;
    private final JLabel labelProducto;
    private Producto producto;
    private int cantidad;
    private PanelIntroduceNumero panelIntroduceNumero;

    public JPanel getPanel() {
        return panel;
    }

    public PanelAnyadeProducto() {
        this.panel = new JPanel(new GridLayout(0,1));
        this.panelIntroduceNumero = new PanelIntroduceNumero();
        this.producto = null;
        this.cantidad = 1;
        labelProducto = new JLabel("No hay ningun producto seleccionado");
        labelProducto.setForeground(Color.BLUE);
        panel.add(labelProducto);
        panel.add(panelIntroduceNumero.getPanel());
    }
    public void cambiaProducto(Producto producto){
        this.producto = producto;
        cambiaTextoProducto(producto.getNombre());
    }
    private void cambiaTextoProducto(String nombreProducto) {
        labelProducto.setText(nombreProducto);
    }


    @Override
    public boolean introduceValor(String texto) {
        try {
            int nuevaCantidad = Integer.parseInt(texto);
            this.cantidad = nuevaCantidad;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LOGGER.warning("Se ha intentado introducir un valor que no es un numero entero: "+texto);
            return false;
        }
        return true;
    }
}
