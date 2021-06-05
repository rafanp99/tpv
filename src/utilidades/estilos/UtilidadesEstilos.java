package utilidades.estilos;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public final class UtilidadesEstilos {
    private UtilidadesEstilos(){ }
    public static void botonAzul(JButton boton){
        boton.setForeground(Color.white);
        //Opaque y border painted solo para que en linux se vean los colores
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setBackground(new Color(0,39,96));
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
    }
    public static void botonCerrar(JButton boton){
        boton.setForeground(Color.WHITE);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        //Opaque y border painted solo para que en linux se vean los colores
        boton.setBackground(new Color(230,57,70));
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
        boton.setBorderPainted(false);
    }
}
