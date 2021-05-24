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

    public JPanel getPanel() {
        return panel;
    }

    public PanelListaCompra(PanelLateral panelLateral) {
        this.panelLateral = panelLateral;
        this.listaCompra = new ArrayList<>();
        this.panel = new JPanel(new GridLayout(0,1));
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
            listaCompra.add(nuevo);
            panel.add(nuevo.getPanel());
        }
        System.out.println(listaCompra);
        return this;
    }

    public boolean eliminaProducto(PanelProductosEnLista producto){
        System.out.println(producto.getProducto().getNombre()+" "+producto.getProducto().getPrecioCentimos());
        int existente = listaCompra.indexOf(producto);
        if(existente>=0){
            PanelProductosEnLista productosEnLista = listaCompra.get(existente);
            panel.remove(productosEnLista.getPanel());
            listaCompra.remove(existente);
            panel.updateUI();
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
