package paneles;

import productos.CategoriaProducto;
import productos.Producto;
import programa.TPVCopisteria;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class PanelProductos {
    private final JPanel panel;
    public final TreeMap<CategoriaProducto,HashSet<Producto>> produtosPorCategoria;
    private final JTabbedPane pestanyas;

    public JPanel getPanel() {
        return panel;
    }

    public PanelProductos(HashSet<Producto> productos) {
        this.panel = new JPanel(new GridLayout(0,1));
        this.produtosPorCategoria = new TreeMap<>();
        this.pestanyas = new JTabbedPane();
        rellenaProductosPorCategorias(productos);
        creaPestanyas();
    }

    private void creaPestanyas() {
        for (Map.Entry<CategoriaProducto,HashSet<Producto>> entrada: produtosPorCategoria.entrySet()) {
            HashSet<Producto> productosDeCategoria = entrada.getValue();
            JPanel panelPestanya = new JPanel(new GridLayout(0,6));
            for (Producto producto:productosDeCategoria) {
                BotonProducto botonProducto = new BotonProducto(producto,this);
                panelPestanya.add(botonProducto.getBoton());
            }
            pestanyas.addTab(entrada.getKey().getNombre(),null,panelPestanya);
        }
        panel.add(pestanyas);
    }

    private void rellenaProductosPorCategorias(HashSet<Producto> productos) {
        System.out.println(productos.size());
        for (Producto producto:productos) {
            for (CategoriaProducto categoria:producto.getCategorias()) {
                if(produtosPorCategoria.containsKey(categoria)){
                    HashSet<Producto> productosDeLaCategoria = produtosPorCategoria.get(categoria);
                    productosDeLaCategoria.add(producto);
                    produtosPorCategoria.put(categoria,productosDeLaCategoria);
                }else{
                    HashSet<Producto> setProductos = new HashSet<>();
                    setProductos.add(producto);
                    produtosPorCategoria.put(categoria,setProductos);
                }
            }
        }
    }

    /*
    public static void main(String[] args) throws IOException {
        PanelProductos panelProductos = new PanelProductos(TPVCopisteria.leeProductos());
        for (Map.Entry<CategoriaProducto,HashSet<Producto>> entrada:panelProductos.produtosPorCategoria.entrySet()){
            System.out.println("Categoria: "+entrada.getKey());
            System.out.println("===============================");
            for (Producto producto:entrada.getValue()) {
                System.out.println("  -  "+producto.getNombre());
            }
        }
    }*/

    private void anyadeBotonesProductos() {
        //TODO
    }

}
