package paneles;

import javax.swing.*;
import java.awt.*;

public class PanelSuperiorFechaHora {
    private final JPanel panel;
    private final GridBagConstraints constraint;

    public JPanel getPanel() {
        return panel;
    }

    public PanelSuperiorFechaHora() {
        constraint = new GridBagConstraints();
        panel = new JPanel(new GridBagLayout());
        anyadeLogo();
        anyadeFechaYHora();
        panel.setBackground(Color.BLUE);
    }

    private void anyadeLogo() {
        //TODO ver tema imagen
        JLabel label = new JLabel("Logo");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        constraint.gridx=0;
        constraint.gridy=0;
        constraint.weightx=2;
        panel.add(label,constraint);
    }

    private void anyadeFechaYHora(){
        //TODO ver bien tema fecha y hora
        JLabel label = new JLabel("Aqui va la fecha y la hora");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        constraint.gridx=1;
        constraint.gridy=0;
        constraint.weightx=0;
        panel.add(label,constraint);
    }
}
