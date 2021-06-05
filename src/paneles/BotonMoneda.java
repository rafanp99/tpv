package paneles;

import logger.LogFactory;
import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.util.logging.Logger;

public class BotonMoneda {
    private final static Logger LOGGER = LogFactory.getLogger();
    private final int valor;
    private final JButton boton;
    private final VentanaCobrar ventanaCobrar;

    public JButton getBoton() {
        return boton;
    }

    public BotonMoneda(VentanaCobrar ventanaCobrar,int valor) {
        this.ventanaCobrar = ventanaCobrar;
        this.valor = valor;
        this.boton = new JButton(valor+"€");
        this.boton.addActionListener(e->{
            ventanaCobrar.anyade(valor);
        });
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
