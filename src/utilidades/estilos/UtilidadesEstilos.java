package utilidades.estilos;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public final class UtilidadesEstilos {
    private UtilidadesEstilos(){ }
    public static void botonAzul(JButton boton){
        // TODO En linux no los colorea de ninguna forma
        boton.setOpaque(true);
        boton.setBackground(new Color(0,39,96));
        boton.setForeground(Color.white);
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,32));
    }
    public static void botonCerrar(JButton boton){
        // TODO En linux no los colorea de ninguna forma
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(230,57,70));
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
        boton.setBorderPainted(false);
    }
}
