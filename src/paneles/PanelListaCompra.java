package paneles;

import logger.LogFactory;
import productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class PanelListaCompra {
    private static final Logger LOGGER = LogFactory.getLogger(PanelListaCompra.class.getName());
    private final PanelLateral panelLateral;
    private final List<PanelProductosEnLista> listaCompra;
    private final JPanel panel;
    private final GridBagConstraints constraints;

    public JPanel getPanel() {
        return panel;
    }

    public PanelListaCompra(PanelLateral panelLateral) {
        this.panelLateral = panelLateral;
        this.listaCompra = new ArrayList<>();
        this.panel = new JPanel(new GridBagLayout());
        this.constraints = new GridBagConstraints();
        this.constraints.gridx=0;
        this.constraints.gridy=0;
        this.constraints.weightx=1;
        this.constraints.fill=GridBagConstraints.HORIZONTAL;
    }

    public int contieneProducto(Producto producto){
        for (int i = 0; i < listaCompra.size() ; i++) {
            if(listaCompra.get(i).getProducto().equals(producto)){
                return i;
            }
        }
        return -1;
    }


    public PanelListaCompra anyadeProducto(Producto producto, int cantidad){
        int posicionProductoSiExiste = contieneProducto(producto);
        if(posicionProductoSiExiste>=0){
            //El producto existe
            PanelProductosEnLista productoEnListaActual = listaCompra.get(posicionProductoSiExiste);
            productoEnListaActual.anyadeCantidad(cantidad);
        }else{
            PanelProductosEnLista nuevo =  new PanelProductosEnLista(producto,cantidad,this);
            constraints.gridy++;
            listaCompra.add(nuevo);
            panel.add(nuevo.getPanel(),constraints);
        }
        return this;
    }

    public boolean eliminaProducto(PanelProductosEnLista producto){
        int existente = listaCompra.indexOf(producto);
        if(existente>=0){
            PanelProductosEnLista productosEnLista = listaCompra.get(existente);
            panelLateral.getPanelBotonPagar().restaPrecio(productosEnLista.getProducto().getPrecioCentimos()*productosEnLista.getCantidad());
            panel.remove(productosEnLista.getPanel());
            listaCompra.remove(existente);
            panel.updateUI();
            constraints.gridy--;
            return true;
        }
        return false;
    }

    public int getTotalEnCentimos(){
        int resultado = 0;
        for (PanelProductosEnLista productoEnLista:listaCompra) {
            int anyadir = productoEnLista.getCantidad()*productoEnLista.getProducto().getPrecioCentimos();
            resultado += anyadir;
        }
        return resultado;
    }
}
