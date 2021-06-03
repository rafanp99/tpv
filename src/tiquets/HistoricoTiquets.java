package tiquets;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class HistoricoTiquets implements Serializable {
    private final List<Tiquet> tiquets;

    public HistoricoTiquets(){
        this.tiquets = new ArrayList<>();
    }

    public List<Tiquet> getTiquets() {
        return tiquets;
    }

    public HistoricoTiquets anyadeTiquet(Tiquet tiquet){
        tiquets.add(tiquet);
        return this;
    }
}
