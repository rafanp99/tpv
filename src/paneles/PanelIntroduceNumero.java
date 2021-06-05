package paneles;

import programa.TPVCopisteria;
import utilidades.estilos.UtilidadesEstilos;
import logger.LogFactory;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.io.Serializable;
import java.util.logging.Logger;

public class PanelIntroduceNumero {
    private final Logger LOGGER = LogFactory.getLogger();
    private final PanelAnyadeProducto panelAnyadeProducto;
    private int cantidad;
    private final JPanel panel;
    private final JButton botonMenos;
    private final JTextField campoCantidad;
    private final JButton botonMas;
    private final JButton x10;
    private final JButton x100;
    private final JButton reiniciar;
    private final JButton aceptar;

    public int getCantidad() {
        checkNumeroField();
        return cantidad;
    }

    public JPanel getPanel() {
        return panel;
    }

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

    public void reinicia(){
        cantidad = 1;
        campoCantidad.setText("1");
    }

    private void checkNumeroField() {
        int valorReal = 1;
        try{
            int valorField = Integer.parseInt(campoCantidad.getText());
            valorReal = valorField;
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
