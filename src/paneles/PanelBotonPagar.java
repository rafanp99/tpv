package paneles;

import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa el panel que incluye el boton para comenzar el cobro del TPV
 * @author Rafael Niñoles Parra
 */
public class PanelBotonPagar {
    private final JPanel panel;
    private final PanelLateral panelLateral;
    private final JButton boton;
    private int precioTotalCentimos;

    /**
     * Suma cantidad en centimos al precio
     * @param sumar cantidad de centimos a sumar
     */
    public void anyadePrecio(int sumar){
        precioTotalCentimos+=sumar;
        actualizaPrecio();
    }

    /**
     * Resta cantidad en centimos al precio
     * @param restar cantidad de centimos a restar
     */
    public void restaPrecio(int restar){
        precioTotalCentimos-=restar;
        actualizaPrecio();
    }

    /**
     * Devuelve el panel con el boton de pagar
     * @return panel con boton de pagar
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Crea un panel que incluye un JButton que realiza la funcion de iniciar el cobro
     * @param panelLateral panel en el que se incluira
     */
    public PanelBotonPagar(PanelLateral panelLateral) {
        this.panelLateral = panelLateral;
        this.precioTotalCentimos = 0;
        this.boton = new JButton();
        this.boton.addActionListener(e-> this.panelLateral.finalizaCompra());
        actualizaPrecio();
        UtilidadesEstilos.botonAzul(boton);
        this.panel = new JPanel(new GridLayout(0,1));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx=1;
        gridBagConstraints.weighty=1;
        this.panel.add(boton,gridBagConstraints);
    }

    private void actualizaPrecio() {
        boton.setText("COBRAR: "+String.format("%.2f",(double) precioTotalCentimos/100)+"€");
    }
}
