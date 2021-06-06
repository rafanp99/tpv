package paneles;

import logger.LogFactory;
import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.util.logging.Logger;

/**
 * Clase que representa los botones de las monedas en el panel de cobro
 * @author Rafael Niñoles Parra
 */
public class BotonMoneda {
    private final static Logger LOGGER = LogFactory.getLogger();
    private final JButton boton;
    private final VentanaCobrar ventanaCobrar;
    private final int valor;

    /**
     * Devuelve el boton de la moneda
     * @return JButton de la moneda
     */
    public JButton getBoton() {
        return boton;
    }

    /**
     * Crea un boton para una moneda
     * @param ventanaCobrar referencia a la ventana en la que se va a realizar el cobro
     * @param valor valor en centimos de la moneda
     */
    public BotonMoneda(VentanaCobrar ventanaCobrar,int valor) {
        this.ventanaCobrar = ventanaCobrar;
        this.valor = valor;
        this.boton = new JButton(String.format("%.2f",(double) valor/100)+"€");
        this.boton.addActionListener(e-> this.ventanaCobrar.anyade(valor));
        disenyaBoton();
    }

    private void disenyaBoton() {
        try {
            ImageIcon imagen = new ImageIcon("resources/"+valor+"moneda.jpg");
            boton.setIcon(imagen);
            boton.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            UtilidadesEstilos.botonAzul(boton);
            LOGGER.warning("No se ha encontrado la imagen de la moneda de "+valor+" centimos");
        }
        boton.setBorder(BorderUIResource.getBlackLineBorderUIResource());
    }
}
