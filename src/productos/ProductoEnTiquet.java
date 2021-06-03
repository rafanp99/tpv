package productos;

import java.io.Serializable;

public class ProductoEnTiquet implements Serializable {
    private final Producto producto;
    private int cantidad;

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ProductoEnTiquet(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductoEnTiquet(Producto producto) {
        this(producto,1);
    }

    public void anyadeCantidad(int cantidad){
        this.cantidad += cantidad;
    }
}
