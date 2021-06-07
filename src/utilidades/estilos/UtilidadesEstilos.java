package utilidades.estilos;

import javax.swing.*;
import java.awt.*;

/**
 * Clase auxiliar de utilidades para dar estilo a elementos de swing
 * @author Rafael Niñoles Parra
 */
public final class UtilidadesEstilos {
    private UtilidadesEstilos(){ }

    /**
     * Aplica estilos a un JButton para que tenga apariencia azul
     * @param boton JButton al que deseas aplicarle los estilos
     */
    public static void botonAzul(JButton boton){
        boton.setForeground(Color.white);
        //Opaque y border painted solo para que en linux se vean los colores
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setBackground(new Color(0,39,96));
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
    }

    /**
     * Aplica estilos a un JButton para que tenga apariencia azul clara
     * @param boton JButton al que deseas aplicarle los estilos
     */
    public static void botonAzulClaro(JButton boton){
        boton.setForeground(Color.white);
        //Opaque y border painted solo para que en linux se vean los colores
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setBackground(new Color(69, 123, 157));
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
    }

    /**
     * Aplica estilos a un JButton para que tenga apariencia de boton para cerrar (rojo)
     * @param boton JButton al que deseas aplicarle los estilos
     */
    public static void botonCerrar(JButton boton){
        boton.setForeground(Color.WHITE);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        //Opaque y border painted solo para que en linux se vean los colores
        boton.setBackground(new Color(230,57,70));
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,14));
        boton.setBorderPainted(false);
    }
}
