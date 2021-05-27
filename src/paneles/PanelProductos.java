package paneles;

import productos.CategoriaProducto;
import productos.Producto;
import programa.TPVCopisteria;
import utilidades.estilos.Vector2;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PanelProductos {
    private final JPanel panel;
    public final TreeMap<CategoriaProducto,HashSet<Producto>> produtosPorCategoria;
    private final JTabbedPane pestanyas;
    private final PanelLateral panelLateral;
    private final int cantidadColumas = 4;

    public PanelLateral getPanelLateral(){
        return panelLateral;
    }

    public JPanel getPanel() {
        return panel;
    }

    public PanelProductos(HashSet<Producto> productos, PanelLateral panelLateral) {
        this.panelLateral = panelLateral;
        this.panel = new JPanel(new GridLayout(0,1));
        this.produtosPorCategoria = new TreeMap<>();
        this.pestanyas = new JTabbedPane();
        rellenaProductosPorCategorias(productos);
        creaPestanyas();
    }

    private void creaPestanyas() {
        // TODO Revisar y limpiar este metodo
        Set<Producto> todosLosProductos = new HashSet<>();
        JPanel pestanyaTodosProductos=new JPanel(new GridBagLayout());
        int posicionTodos=0;
        GridBagConstraints cTodos = new GridBagConstraints();
        cTodos.fill = Box.Filler.HEIGHT;
        for (Map.Entry<CategoriaProducto,HashSet<Producto>> entrada: produtosPorCategoria.entrySet()) {
            HashSet<Producto> productosDeCategoria = entrada.getValue();
            JPanel panelPestanya = new JPanel(new GridBagLayout());
            int posicionActual = 0;
            GridBagConstraints c = new GridBagConstraints();
            c.fill = Box.Filler.HEIGHT;
            for (Producto producto:productosDeCategoria) {
                BotonProducto botonProducto = new BotonProducto(producto,this);
                Vector2 posicion = calculaPosicionBoton(posicionActual);
                c.gridx=posicion.getX();
                c.gridy=posicion.getY();
                panelPestanya.add(botonProducto.getBoton(),c);
                posicionActual++;
                if(!todosLosProductos.contains(producto)){
                    todosLosProductos.add(producto);
                    BotonProducto botonProductoParaTodos = new BotonProducto(producto,this);
                    Vector2 posicionParaTodos = calculaPosicionBoton(posicionTodos);
                    posicionTodos++;
                    cTodos.gridx=posicionParaTodos.getX();
                    cTodos.gridy=posicionParaTodos.getY();
                    pestanyaTodosProductos.add(botonProductoParaTodos.getBoton(),cTodos);
                }
            }
            pestanyas.addTab(entrada.getKey().getNombre(),null,panelPestanya);
        }
        pestanyas.addTab("TODOS",null,pestanyaTodosProductos);
        panel.add(pestanyas);
    }
    private Vector2 calculaPosicionBoton(int posicion){
        int fila = posicion/cantidadColumas;
        int columna = posicion%cantidadColumas;
        return new Vector2(columna,fila);
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

    // TODO Eliminar este comentario cuando finalice
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

}
