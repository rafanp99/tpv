package paneles;

import logger.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class PanelLateral {
    private static final Logger LOGGER = LogFactory.getLogger(PanelLateral.class.getName());
    private final JPanel panel;
    private final PanelAnyadeProducto panelAnyadeProducto;

    public JPanel getPanel() {
        return panel;
    }

    public PanelLateral(){
        this.panel = new JPanel(new GridLayout(0,1));
        this.panelAnyadeProducto = new PanelAnyadeProducto();
        this.panel.add(panelAnyadeProducto.getPanel());
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        ventana.add(new PanelLateral().getPanel());
        ventana.pack();
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
