package paneles;

import utilidades.estilos.UtilidadesEstilos;
import logger.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class PanelIntroduceNumero {
    private final Logger LOGGER = LogFactory.getLogger(PanelIntroduceNumero.class.getName());
    private final PanelAnyadeProducto panelAnyadeProducto;
    private int cantidad;
    private final JPanel panel;
    private final JButton botonMenos;
    private final JTextField campoCantidad;
    private final JButton botonMas;
    private final JButton x10;
    private final JButton x100;
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
        this.panel = new JPanel(new GridLayout(1,6));
        this.cantidad = 1;
        this.botonMenos = new JButton("-");
        UtilidadesEstilos.botonAzul(botonMenos);
        panel.add(botonMenos);
        this.campoCantidad = new JTextField("1");
        campoCantidad.setHorizontalAlignment(SwingConstants.CENTER);
        campoCantidad.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,32));
        campoCantidad.setColumns(4);
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
                campoCantidad.setText(""+cantidad);
            }
        });
        botonMas.addActionListener(e->{
            checkNumeroField();
            cantidad ++;
            campoCantidad.setText(""+cantidad);
        });
        x10.addActionListener(e->{
            checkNumeroField();
            cantidad *= 10;
            campoCantidad.setText(""+cantidad);
        });
        x100.addActionListener(e->{
            checkNumeroField();
            cantidad *= 100;
            campoCantidad.setText(""+cantidad);
        });
        aceptar.addActionListener(e->{
            checkNumeroField();
            if(panelAnyadeProducto.getProducto()!=null){
                panelAnyadeProducto.aceptar();
            }
        });
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LOGGER.info("Se ha introducido un campo no numerico en el campo de cantidad: "+campoCantidad.getText());
        }
        cantidad = valorReal;
        campoCantidad.setText(""+cantidad);
    }
}
