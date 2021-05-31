package paneles;

import programa.TPVCopisteria;
import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaCobrar {
    private final int columnas;
    private final JFrame ventana;
    private final JPanel panel;
    private final PanelListaCompra panelListaCompra;
    private final JLabel labelACobrar;
    private final GridBagConstraints constraints;
    private final JLabel labelCambio;
    private final List<BotonMoneda> botonesMonedas;
    private final JButton botonFinalizar;
    private final JButton botonFinalizarEImprimir;
    private final JButton botonBorrar;
    private int aCobrar;
    private int cambio;

    public VentanaCobrar(PanelListaCompra panelListaCompra) {
        this.panel = new JPanel(new GridBagLayout());
        this.panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        this.ventana = new JFrame();
        this.columnas=5;
        this.panelListaCompra = panelListaCompra;
        this.botonesMonedas = new ArrayList<>();
        this.aCobrar = 0;
        this.cambio = 0;
        this.botonFinalizar = new JButton("Finalizar");
        this.botonFinalizarEImprimir = new JButton("Finalizar e imprimir");
        configuraBotonesFinalizar();
        this.botonBorrar = new JButton("Borrar");
        configuraBotonBorrar();
        this.labelACobrar = new JLabel("Total: "+String.format("%.2f",(double) aCobrar/100)+"€");
        this.labelCambio = new JLabel("Cambio: "+String.format("%.2f",(double) cambio/100)+"€");
        configuraLabelsInicio();
        this.constraints = new GridBagConstraints();
        anyadeMonedas();
        rellenaVentana();
        this.ventana.add(panel);
        this.ventana.pack();
        this.ventana.setLocationRelativeTo(null);
        this.ventana.setVisible(false);
    }

    private void configuraLabelsInicio() {
        this.labelCambio.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
        this.labelACobrar.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
    }

    private void configuraBotonesFinalizar() {
        UtilidadesEstilos.botonAzul(botonFinalizar);
        botonFinalizar.addActionListener(e->{
            creaTiquet();
        });
        UtilidadesEstilos.botonAzul(botonFinalizarEImprimir);
        botonFinalizarEImprimir.addActionListener(e->{
            creaTiquet();
            imprimeTiquet();
        });
    }

    private void imprimeTiquet() {
        //TODO
        if(!checkeaCambio())return;
    }

    private void creaTiquet() {
        //TODO
        if(!checkeaCambio())return;
    }

    private boolean checkeaCambio() {
        if(cambio<0){
            JOptionPane.showMessageDialog(TPVCopisteria.FRAME,"¡Aun queda dinero por cobrar!");
            return false;
        }
        return true;
    }

    private void configuraBotonBorrar() {
        UtilidadesEstilos.botonDanger(botonBorrar);
        botonBorrar.addActionListener(e->{
            reiniciaCobro();
        });
    }

    private void actualizaLabels(){
        labelACobrar.setText("Total: "+String.format("%.2f",(double) aCobrar/100)+"€");
        labelCambio.setText("Cambio: "+String.format("%.2f",(double) cambio/100)+"€");
    }

    private void anyadeMonedas() {
        //Billetes
        botonesMonedas.add(new BotonMoneda(this,10000));
        botonesMonedas.add(new BotonMoneda(this,5000));
        botonesMonedas.add(new BotonMoneda(this,2000));
        botonesMonedas.add(new BotonMoneda(this,1000));
        botonesMonedas.add(new BotonMoneda(this,500));
        //Monedas
        botonesMonedas.add(new BotonMoneda(this,200));
        botonesMonedas.add(new BotonMoneda(this,100));
        botonesMonedas.add(new BotonMoneda(this,50));
        botonesMonedas.add(new BotonMoneda(this,20));
        botonesMonedas.add(new BotonMoneda(this,10));
        botonesMonedas.add(new BotonMoneda(this,5));
        botonesMonedas.add(new BotonMoneda(this,2));
        botonesMonedas.add(new BotonMoneda(this,1));
    }

    private void rellenaVentana(){
        rellenaTextoInicial();
        rellenaMonedas();
        constraints.gridx=0;
        constraints.gridy++;
        constraints.gridwidth=2;
        panel.add(botonFinalizar,constraints);
        constraints.gridx=2;
        constraints.gridwidth=3;
        panel.add(botonFinalizarEImprimir,constraints);
    }

    private void rellenaMonedas() {
        constraints.gridy=1;
        constraints.gridx=0;
        for (BotonMoneda botonMoneda:botonesMonedas) {
            panel.add(botonMoneda.getBoton(),constraints);
            if(constraints.gridx==columnas-1){
                constraints.gridx=0;
                constraints.gridy++;
            }else{
                constraints.gridx++;
            }
        }
    }

    private void rellenaTextoInicial() {
        constraints.gridwidth=1;
        constraints.gridheight=1;
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth=2;
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.anchor=GridBagConstraints.NORTH;
        panel.add(labelACobrar,constraints);
        constraints.gridx=2;
        panel.add(labelCambio,constraints);
        constraints.gridwidth=1;
        constraints.gridx=4;
        panel.add(botonBorrar,constraints);
    }

    public void abrir(){
        if(panelListaCompra.getTotalEnCentimos()==0){
            JOptionPane.showMessageDialog(TPVCopisteria.FRAME,"No hay nada que cobrar");
            return;
        }
        this.ventana.setVisible(true);
    }
    public void cerrar(){
        this.ventana.setVisible(false);
    }
    public void anyade(int valor) {
        cambio+=valor;
        actualizaLabels();
    }

    public void reiniciaCobro() {
        aCobrar = panelListaCompra.getTotalEnCentimos();
        cambio = aCobrar*-1;
        actualizaLabels();
    }
}
