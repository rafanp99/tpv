package paneles;

import logger.LogFactory;
import productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class PanelListaCompra {
    private static final Logger LOGGER = LogFactory.getLogger(PanelListaCompra.class.getName());
    private final PanelLateral panelLateral;
    private final Map<Producto,Integer> listaCompra;
    private final JPanel panel;

    public PanelListaCompra(PanelLateral panelLateral) {
        this.panelLateral = panelLateral;
        this.listaCompra = new LinkedHashMap<>();
        this.panel = new JPanel(new GridLayout(0,1));
    }

    public PanelListaCompra anyadeProducto(Producto producto, int cantidad){
        if (listaCompra.containsKey(producto)){
            int cantidadAntigua = listaCompra.get(producto);
            int cantidadNueva = cantidadAntigua + cantidad;
            listaCompra.put(producto,cantidadNueva);
            System.out.println("cambiado el producto "+producto.getNombre()+" a "+cantidadNueva);
        }else{
            listaCompra.put(producto,cantidad);
            System.out.println("añadido el producto "+producto.getNombre());
        }
        return this;
    }

    public PanelListaCompra eliminaProducto(Producto producto){
        listaCompra.remove(producto);
        return this;
    }

    public int getTotalEnCentimos(){
        int resultado = 0;
        for (Map.Entry<Producto,Integer> entrada:listaCompra.entrySet()) {
            int precioProducto = entrada.getKey().getPrecioCentimos();
            int cantidad = entrada.getValue();
            int preciototalProducto = precioProducto * cantidad;
            resultado += preciototalProducto;
        }
        return resultado;
    }
}
