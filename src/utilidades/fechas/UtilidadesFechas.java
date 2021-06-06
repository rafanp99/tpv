package utilidades.fechas;

import java.time.LocalDateTime;

/**
 * Clase auxiliar para dar formato a fechas para España
 * @author Rafael Niñoles Parra
 */
public final class UtilidadesFechas {
    private UtilidadesFechas(){}

    /**
     * Devuelve cadena con una fecha formateada para España
     * @param fecha Fecha a formatear
     * @return cadena con una fecha formateada para España
     */
    public static String getFechaFormateada(LocalDateTime fecha){
        String salida = "";
        salida+=fecha.getDayOfMonth()+"/";
        salida+=fecha.getMonthValue()+"/";
        salida+=fecha.getYear();
        return salida;
    }

    /**
     * Devuelve cadena con la fecha y hora formateada para España
     * @param fecha Fecha a formatear
     * @return cadena con la fecha y hora formateada para España
     */
    public static String getFechaYHoraFormateada(LocalDateTime fecha){
        String salida="";
        salida += getFechaFormateada(fecha);
        salida += " ";
        salida += getHoraFormateada(fecha);
        return salida;
    }

    /**
     * Devuelve cadena con la hora formateada para España
     * @param fecha Fecha a formatear
     * @return cadena con la hora formateada para España
     */
    public static String getHoraFormateada(LocalDateTime fecha){
        String salida = "";
        salida+=fecha.getHour()+":";
        salida+=fecha.getMinute()+":";
        salida+=fecha.getSecond();
        return salida;
    }
}
