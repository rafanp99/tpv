package paneles;

import jdk.nashorn.internal.scripts.JD;
import programa.TPVCopisteria;
import tiquets.Tiquet;
import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaCobrar {
    private final int columnas;
    private final JDialog ventana;
    private final JPanel panel;
    private final PanelListaCompra panelListaCompra;
    private final JLabel labelACobrar;
    private final GridBagConstraints constraints;
    private final JLabel labelEntregado;
    private final JLabel labelCambio;
    private final List<BotonMoneda> botonesMonedas;
    private final JButton botonFinalizar;
    private final JButton botonFinalizarEImprimir;
    private final JButton botonBorrar;
    private int aCobrar;
    private int entregado;
    private int cambio;

    public VentanaCobrar(PanelListaCompra panelListaCompra) {
        this.panel = new JPanel(new GridBagLayout());
        this.panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        this.ventana = new JDialog(TPVCopisteria.FRAME,"Cobro",true);
        this.ventana.setLocationRelativeTo(TPVCopisteria.FRAME);
        this.columnas=6;
        this.panelListaCompra = panelListaCompra;
        this.botonesMonedas = new ArrayList<>();
        this.aCobrar = 0;
        this.cambio = 0;
        this.entregado = 0;
        this.botonFinalizar = new JButton("Finalizar");
        this.botonFinalizarEImprimir = new JButton("Finalizar e imprimir");
        configuraBotonesFinalizar();
        this.botonBorrar = new JButton("Borrar");
        this.labelEntregado=new JLabel("Entregado: "+String.format("%.2f",(double) entregado/100)+"€");
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
        this.labelEntregado.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
    }

    private void configuraBotonesFinalizar() {
        UtilidadesEstilos.botonAzul(botonFinalizar);
        botonFinalizar.addActionListener(e->{
            creaTiquet();
        });
        UtilidadesEstilos.botonAzul(botonFinalizarEImprimir);
        botonFinalizarEImprimir.addActionListener(e->{
            Tiquet tiquet = new Tiquet(panelListaCompra.getListaCompra());
            imprimeTiquet();
        });
    }

    private void imprimeTiquet() {
        //TODO
    }

    private void creaTiquet() {
        //TODO
        if(!checkeaCambio())return;
        Tiquet tiquetActual = new Tiquet(panelListaCompra.getListaCompra());
        TPVCopisteria.anyadeTiquetAHistorico(tiquetActual);
        reiniciaCobro();
        panelListaCompra.vaciar();
        cerrar();
    }

    private boolean checkeaCambio() {
        if(entregado<aCobrar){
            JOptionPane.showMessageDialog(TPVCopisteria.FRAME,"¡Aun queda dinero por cobrar!");
            return false;
        }
        return true;
    }

    private void configuraBotonBorrar() {
        UtilidadesEstilos.botonCerrar(botonBorrar);
        botonBorrar.addActionListener(e-> reiniciaCobro());
    }

    private void actualizaLabels(){
        labelACobrar.setText("Total: "+String.format("%.2f",(double) aCobrar/100)+"€");
        labelCambio.setText("Cambio: "+String.format("%.2f",(double) cambio/100)+"€");
        labelEntregado.setText("Entregado: "+String.format("%.2f",(double) entregado/100)+"€");
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
        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.weightx=1;
        constraints.gridwidth=3;
        panel.add(botonFinalizar,constraints);
        constraints.gridwidth=4;
        constraints.gridx=3;
        panel.add(botonFinalizarEImprimir,constraints);
    }

    private void rellenaMonedas() {
        constraints.gridy=1;
        constraints.gridx=0;
        for (BotonMoneda botonMoneda:botonesMonedas) {
            panel.add(botonMoneda.getBoton(),constraints);
            if(constraints.gridx>=columnas){
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
        panel.add(labelEntregado,constraints);
        constraints.gridx=4;
        panel.add(labelCambio,constraints);
        constraints.gridwidth=1;
        constraints.gridx=6;
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
        entregado+=valor;
        cambio+=valor;
        actualizaLabels();
    }

    public void reiniciaCobro() {
        aCobrar = panelListaCompra.getTotalEnCentimos();
        cambio = aCobrar*-1;
        entregado = 0;
        actualizaLabels();
    }
}
