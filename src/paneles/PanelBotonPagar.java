package paneles;

import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import java.awt.*;

public class PanelBotonPagar {
    private final JPanel panel;
    private final PanelLateral panelLateral;
    private final JButton boton;
    private int precioTotalCentimos;

    public void anyadePrecio(int sumar){
        precioTotalCentimos+=sumar;
        actualizaPrecio();
    }

    public void restaPrecio(int restar){
        precioTotalCentimos-=restar;
        actualizaPrecio();
    }

    public JPanel getPanel() {
        return panel;
    }

    public PanelBotonPagar(PanelLateral panelLateral) {
        this.panelLateral = panelLateral;
        this.precioTotalCentimos = 0;
        this.boton = new JButton();
        this.boton.addActionListener(e->{
            panelLateral.finalizaCompra();
        });
        actualizaPrecio();
        UtilidadesEstilos.botonAzul(boton);
        this.panel = new JPanel(new GridLayout(0,1));
        this.panel.add(boton);
    }

    private void actualizaPrecio() {
        boton.setText("COBRAR: "+String.format("%.2f",(double) precioTotalCentimos/100)+"€");
    }
}
