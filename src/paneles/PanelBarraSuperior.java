package paneles;

import programa.TPVCopisteria;
import utilidades.estilos.UtilidadesEstilos;

import javax.swing.*;
import java.awt.*;

public class PanelBarraSuperior {
    private final JPanel panel;
    private final GridBagConstraints constraint;
    private final JButton botonGuardaHistorico;

    public JPanel getPanel() {
        return panel;
    }

    public PanelBarraSuperior() {
        constraint = new GridBagConstraints();
        constraint.gridheight=2;
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0,39,96));
        botonGuardaHistorico = new JButton("Guardar historico Tiquets");
        botonGuardaHistorico.addActionListener(e->{
            TPVCopisteria.guardaHistorico();
        });
        UtilidadesEstilos.botonAzul(botonGuardaHistorico);
        anyadeLogo();
        anyadeBotones();
        anyadeFechaYHora();
    }
    private void anyadeLogo() {
        //TODO ver tema imagen
        JLabel label = new JLabel("Logo");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        constraint.gridx=0;
        constraint.gridy=0;
        constraint.weightx=1;
        panel.add(label,constraint);
    }

    private void anyadeBotones() {
        constraint.gridx=1;
        constraint.gridy=1;
        constraint.weightx=1;
        panel.add(botonGuardaHistorico);
    }


    private void anyadeFechaYHora(){
        //TODO ver bien tema fecha y hora
        JLabel label = new JLabel("Aqui va la fecha y la hora");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        constraint.gridx=2;
        constraint.gridy=0;
        constraint.weightx=1;
        panel.add(label,constraint);
    }
}
