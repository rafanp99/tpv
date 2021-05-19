package productos;

import java.io.Serializable;
import java.util.*;

public class ListaProductos implements Serializable {
    private final Set<Producto> productos;

    public ListaProductos() {
        this.productos = new HashSet<>();
    }

    public ListaProductos anyadeProducto(Producto producto){
        productos.add(producto);
        return this;
    }

    public Set<Producto> getProductos() {
        return new HashSet<>(productos);
    }
}
