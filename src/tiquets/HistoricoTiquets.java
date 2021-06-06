package tiquets;

import programa.TPVCopisteria;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase encargada de guardar tiquets, usada en el historico global y en el de la sesion
 * @author Rafael Niñoles Parra
 */
public class HistoricoTiquets implements Serializable {
    private final List<Tiquet> tiquets;

    /**
     * Crea un historico de tiquets
     */
    public HistoricoTiquets(){
        this.tiquets = new ArrayList<>();
    }

    /**
     * Devuelve la lista de tiquets del historico
     * @return lista de tiquets del historico
     */
    public List<Tiquet> getTiquets() {
        return tiquets;
    }

    /**
     * Añade un tiquet al historico
     * @param tiquet Tiquet a añadir
     * @return Devuelve la instacion del propio HistoricoTiquets
     */
    public HistoricoTiquets anyadeTiquet(Tiquet tiquet){
        tiquets.add(tiquet);
        TPVCopisteria.guardaHistorico();
        return this;
    }
}
