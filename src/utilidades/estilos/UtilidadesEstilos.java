package utilidades.estilos;

import javax.swing.*;
import java.awt.*;

public final class UtilidadesEstilos {
    private UtilidadesEstilos(){ }
    public static void botonAzul(JButton boton){
        boton.setBackground(Color.BLUE);
        boton.setForeground(Color.white);
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,32));
    }
}
