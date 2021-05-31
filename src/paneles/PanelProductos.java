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
        this.pestanyas.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
        rellenaProductosPorCategorias(productos);
        creaPestanyas();
    }

    private void creaPestanyas() {
        // TODO Revisar y limpiar este metodo
        Set<Producto> todosLosProductos = new HashSet<>();
        JPanel pestanyaTodosProductos=new JPanel(new GridLayout(5,5));
        int posicionTodos=0;
        GridBagConstraints cTodos = new GridBagConstraints();
        cTodos.fill = GridBagConstraints.HORIZONTAL;
        for (Map.Entry<CategoriaProducto,HashSet<Producto>> entrada: produtosPorCategoria.entrySet()) {
            HashSet<Producto> productosDeCategoria = entrada.getValue();
            JPanel panelPestanya = new JPanel(new GridLayout(5,5));
            int posicionActual = 0;
            for (Producto producto:productosDeCategoria) {
                BotonProducto botonProducto = new BotonProducto(producto,this);
                panelPestanya.add(botonProducto.getBoton());
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
            for (int i=(25-productosDeCategoria.size()); i>0; i--){
                JButton boton = new JButton();
                boton.setVisible(false);
                panelPestanya.add(boton);
            }
            pestanyas.addTab(entrada.getKey().getNombre(),null,panelPestanya);
        }
        for (int i=(25-todosLosProductos.size()); i>0; i--){
            JButton boton = new JButton();
            boton.setVisible(false);
            pestanyaTodosProductos.add(boton);
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
