package paneles;

import productos.CategoriaProducto;
import productos.Producto;
import utilidades.matematicas.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Clase que representa un panel que incluye pestañas con productos filtrados por categorias
 * @author Rafael Niñoles Parra
 */
public class PanelProductos {
    private final JPanel panel;
    public final TreeMap<CategoriaProducto,HashSet<Producto>> produtosPorCategoria;
    private final JTabbedPane pestanyas;
    private final PanelLateral panelLateral;
    private final int cantidadColumas = 6;

    /**
     * Devuelve el la instancia del panel lateral
     * @return instancia del panel lateral
     */
    public PanelLateral getPanelLateral(){
        return panelLateral;
    }

    /**
     * Devuelve el JPanel completo
     * @return el JPanel completo
     */
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
        JPanel pestanyaTodosProductos=new JPanel(new GridLayout(5,cantidadColumas));
        int posicionTodos=0;
        GridBagConstraints cTodos = new GridBagConstraints();
        cTodos.fill = GridBagConstraints.HORIZONTAL;
        //Recorre cada categoria del mapa y sus productos
        for (Map.Entry<CategoriaProducto,HashSet<Producto>> entrada: produtosPorCategoria.entrySet()) {
            HashSet<Producto> productosDeCategoria = entrada.getValue();
            //Creo una pestanya para la categoria actual
            JPanel panelPestanya = new JPanel(new GridLayout(5,cantidadColumas));
            int posicionActual = 0;
            for (Producto producto:productosDeCategoria) {
                //Voy añadiendo el boton de cada producto a la pestaña de su categoria
                BotonProducto botonProducto = new BotonProducto(producto,this);
                panelPestanya.add(botonProducto.getBoton());
                posicionActual++;
                if(!todosLosProductos.contains(producto)){
                    //Verifico que el producto no existe en la categoria de todos, si no existe lo agrego
                    todosLosProductos.add(producto);
                    BotonProducto botonProductoParaTodos = new BotonProducto(producto,this);
                    Vector2 posicionParaTodos = calculaPosicionBoton(posicionTodos);
                    posicionTodos++;
                    //Calculo la posicion de X e Y gracias a saber cuantas columnas tiene la pestaña
                    cTodos.gridx=posicionParaTodos.getX();
                    cTodos.gridy=posicionParaTodos.getY();
                    pestanyaTodosProductos.add(botonProductoParaTodos.getBoton(),cTodos);
                }
            }
            //Relleno la pestaña de la categoria con botones en blanco para que asi todos ocupe en el mismo espacio y esten aliandos arriba izq
            for (int i=(5*cantidadColumas-productosDeCategoria.size()); i>0; i--){
                JButton boton = new JButton();
                boton.setVisible(false);
                panelPestanya.add(boton);
            }
            pestanyas.addTab(entrada.getKey().getNombre(),null,panelPestanya);
        }
        //Al igual que en la pestaña de las categorias relleno de botones en blanco ahora para la pestaña de todos
        for (int i=(5*cantidadColumas-todosLosProductos.size()); i>0; i--){
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

}
