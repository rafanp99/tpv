package paneles;

import programa.TPVCopisteria;
import utilidades.estilos.UtilidadesEstilos;
import logger.LogFactory;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.logging.Logger;

/**
 * Clase que representa el panel que tendra los numeros y opciones necesarias para modificiar la cantidad
 * @author Rafael Niñoles Parra
 */
public class PanelIntroduceNumero {
    private final Logger LOGGER = LogFactory.getLogger();
    private final PanelAnyadeProducto panelAnyadeProducto;
    private final JPanel panel;
    private final JButton botonMenos;
    private final JTextField campoCantidad;
    private final JButton botonMas;
    private final JButton x10;
    private final JButton x100;
    private final JButton reiniciar;
    private final JButton aceptar;
    private int cantidad;

    /**
     * Devuelve la cantidad actual
     * @return cantidad actual
     */
    public int getCantidad() {
        checkNumeroField();
        return cantidad;
    }

    /**
     * Devuelve el panel de introducir numero
     * @return JPanel de introducir numero
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Crea un nuevo panel que sera capaz de modificar un numero de manera sencilla de forma tactil
     * @param panelAnyadeProducto Panel de añadir producto
     */
    public PanelIntroduceNumero(PanelAnyadeProducto panelAnyadeProducto) {
        this.panelAnyadeProducto = panelAnyadeProducto;
        this.panel = new JPanel(new GridLayout(1,0));
        this.cantidad = 1;
        this.botonMenos = new JButton("-");
        UtilidadesEstilos.botonAzul(botonMenos);
        panel.add(botonMenos);
        this.campoCantidad = new JTextField("1");
        campoCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        campoCantidad.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,32));
        campoCantidad.setColumns(5);
        campoCantidad.setBorder(BorderUIResource.getBlackLineBorderUIResource());
        panel.add(campoCantidad);
        this.botonMas = new JButton("+");
        UtilidadesEstilos.botonAzul(botonMas);
        panel.add(botonMas);
        this.x10 = new JButton("x10");
        UtilidadesEstilos.botonAzul(x10);
        panel.add(x10);
        this.x100 = new JButton("x100");
        UtilidadesEstilos.botonAzul(x100);
        panel.add(x100);
        this.reiniciar = new JButton("↺");
        UtilidadesEstilos.botonAzul(reiniciar);
        panel.add(reiniciar);
        this.aceptar = new JButton("✓");
        UtilidadesEstilos.botonAzul(aceptar);
        panel.add(aceptar);
        anyadeListeners();
    }

    /**
     * Reinicia el numero a 1
     */
    public void reinicia(){
        cantidad = 1;
        campoCantidad.setText("1");
    }

    private void anyadeListeners() {
        botonMenos.addActionListener(e->{
            checkNumeroField();
            if(cantidad-1 > 0){
                cantidad --;
                actualizaField();
            }
        });
        botonMas.addActionListener(e->{
            checkNumeroField();
            cantidad ++;
            actualizaField();
        });
        x10.addActionListener(e->{
            checkNumeroField();
            cantidad *= 10;
            actualizaField();
        });
        x100.addActionListener(e->{
            checkNumeroField();
            cantidad *= 100;
            actualizaField();
        });
        reiniciar.addActionListener(e->{
            cantidad = 1;
            actualizaField();
        });
        aceptar.addActionListener(e->{
            checkNumeroField();
            if(panelAnyadeProducto.getProducto()!=null){
                panelAnyadeProducto.aceptar();
            }else{
                JOptionPane.showMessageDialog(TPVCopisteria.FRAME,"No hay ningun producto seleccionado!");
            }
        });
    }

    private void actualizaField() {
        campoCantidad.setText(""+cantidad);
    }


    /**
     * Checkea si el input del numero no es nada raro, si no es un numero correcto lo devolvera a 1
     */
    private void checkNumeroField() {
        int valorReal = 1;
        try{
            valorReal = Integer.parseInt(campoCantidad.getText());
            if(valorReal<1){
                JOptionPane.showMessageDialog(TPVCopisteria.FRAME,"No puede haber un producto con cantidad negativa");
                valorReal=1;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LOGGER.info("Se ha introducido un campo no numerico en el campo de cantidad: "+campoCantidad.getText());
        }
        cantidad = valorReal;
        actualizaField();
    }
}
