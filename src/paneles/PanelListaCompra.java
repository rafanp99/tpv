package paneles;

import logger.TPVLogger;
import productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clase que representa la parte de la lista de la compra en el TPV
 * @author Rafael Niñoles Parra
 */
public class PanelListaCompra {
    private static final Logger LOGGER = TPVLogger.getLogger();
    private final PanelLateral panelLateral;
    private final List<PanelProductosEnLista> listaCompra;
    private final JPanel panel;
    private final GridBagConstraints constraints;

    /**
     * Devuelve el panel de la lista de la compra
     * @return panel de la lista de la compra
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Devuelve la lista de la compra
     * @return lista de productos en la lista de la compra
     */
    public List<PanelProductosEnLista> getListaCompra() {
        return listaCompra;
    }

    /**
     * Crea un panel que se encargara de la lista de la compra
     * @param panelLateral panel lateral en el que se creara
     */
    public PanelListaCompra(PanelLateral panelLateral) {
        this.panelLateral = panelLateral;
        this.listaCompra = new ArrayList<>();
        this.panel = new JPanel(new GridBagLayout());
        this.constraints = new GridBagConstraints();
        this.constraints.gridx=0;
        this.constraints.gridy=0;
        this.constraints.weightx=1;
        this.constraints.weighty=1;
        this.constraints.fill=GridBagConstraints.HORIZONTAL;
    }

    /**
     * Verificara si la lista contiene un producto, si no lo contiene devolvera -1 si no devolvera la posicion del producto
     * @param producto Producto que desea saber si existe
     * @return -1 si no existe, indice en la lista si existe
     */
    public int contieneProducto(Producto producto){
        for (int i = 0; i < listaCompra.size() ; i++) {
            if(listaCompra.get(i).getProductoEnTiquet().getProducto().equals(producto)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Añade un producto a la lista, si ya esta le añadira solo la cantidad
     * @param producto Producto que desea añadir
     * @param cantidad Cantidad del producto que se desea añadir
     * @return Devuelve la misma istancia del Objeto
     */
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

    /**
     * Elimina un producto de la lista si este existe
     * @param producto Producto que desea eliminar
     * @return Booleano que indica si ha sido eliminado o no (Si no existe no lo puede eliminar)
     */
    public boolean eliminaProducto(PanelProductosEnLista producto){
        int existente = listaCompra.indexOf(producto);
        if(existente>=0){
            PanelProductosEnLista productosEnLista = listaCompra.get(existente);
            panelLateral.getPanelBotonPagar().restaPrecio(productosEnLista.getProductoEnTiquet().getProducto().getPrecioCentimos()*productosEnLista.getProductoEnTiquet().getCantidad());
            panel.remove(productosEnLista.getPanel());
            listaCompra.remove(existente);
            panel.updateUI();
            constraints.gridy--;
            return true;
        }
        return false;
    }

    /**
     * Devuelve el precio total de la lista en centimos
     * @return precio total de la lista en centimos
     */
    public int getTotalEnCentimos(){
        int resultado = 0;
        for (PanelProductosEnLista productoEnLista:listaCompra) {
            int anyadir = productoEnLista.getProductoEnTiquet().getCantidad()*productoEnLista.getProductoEnTiquet().getProducto().getPrecioCentimos();
            resultado += anyadir;
        }
        return resultado;
    }

    /**
     * Vacia la lista de la compra
     */
    public void vaciar() {
        for (int i=listaCompra.size()-1; listaCompra.size()>0;i--){
            eliminaProducto(listaCompra.get(i));
        }
    }
}
