package productos;

import java.io.Serializable;
import java.util.*;

public class ListaProductos implements Serializable {
    private final List<Producto> productos;

    public ListaProductos() {
        this.productos = new ArrayList<>();
    }

    public ListaProductos anyadeProducto(Producto producto){
        productos.add(producto);
        return this;
    }

    public ArrayList<Producto> getProductos() {
        return new ArrayList<>(productos);
    }
}
