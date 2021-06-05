package utilidades.fechas;

import java.time.LocalDateTime;

public final class UtilidadesFechas {
    private UtilidadesFechas(){}

    public static String getFechaFormateada(LocalDateTime fecha){
        String salida = "";
        salida+=fecha.getDayOfMonth()+"/";
        salida+=fecha.getMonthValue()+"/";
        salida+=fecha.getYear();
        return salida;
    }
    public static String getFechaYHoraFormateada(LocalDateTime fecha){
        String salida="";
        salida += getFechaFormateada(fecha);
        salida += " ";
        salida += getHoraFormateada(fecha);
        return salida;
    }
    public static String getHoraFormateada(LocalDateTime fecha){
        String salida = "";
        salida+=fecha.getHour()+":";
        salida+=fecha.getMinute()+":";
        salida+=fecha.getSecond();
        return salida;
    }
}
