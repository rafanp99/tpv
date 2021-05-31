package utilidades.estilos;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public final class UtilidadesEstilos {
    private UtilidadesEstilos(){ }
    public static void botonAzul(JButton boton){
        // TODO En linux no los colorea de ninguna forma
        boton.setBackground(Color.BLUE);
        boton.setForeground(Color.white);
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,32));
    }
    public static void botonDanger(JButton boton){
        // TODO En linux no los colorea de ninguna forma
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.RED);
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
        boton.setBorderPainted(false);
    }
}
