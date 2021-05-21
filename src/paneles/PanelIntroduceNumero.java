package paneles;

import javax.swing.*;
import java.awt.*;

public class PanelIntroduceNumero {
    private final JPanel panel;
    private final JButton botonMenos;
    private final JTextField campoCantidad;
    private final JButton botonMas;
    private final JButton x10;
    private final JButton x100;
    private final JButton aceptar;

    public JPanel getPanel() {
        return panel;
    }

    public PanelIntroduceNumero() {
        this.panel = new JPanel(new GridLayout(1,6));
        this.botonMenos = new JButton("-");
        panel.add(botonMenos);
        this.campoCantidad = new JTextField("1");
        campoCantidad.setColumns(4);
        panel.add(campoCantidad);
        this.botonMas = new JButton("+");
        panel.add(botonMas);
        this.x10 = new JButton("x10");
        panel.add(x10);
        this.x100 = new JButton("x100");
        panel.add(x100);
        this.aceptar = new JButton("✓");
        panel.add(aceptar);
    }
}
