package productos;

import java.io.Serializable;

/**
 * Clase que un producto en un tiquet, cuenta con el producto y la cantidad del mismo
 * @author Rafael Niñoles Parra
 */
public class ProductoEnTiquet implements Serializable {
    private final Producto producto;
    private int cantidad;

    /**
     * Devuelve el producto
     * @return producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Devuelve la cantidad del producto
     * @return cantidad del producto
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Crea un nuevo producto en tiquet
     * @param producto Producto
     * @param cantidad Cantidad del producto
     */
    public ProductoEnTiquet(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Crea un nuevo producto en tiquet con 1 de cantidad
     * @param producto Producto
     */
    public ProductoEnTiquet(Producto producto) {
        this(producto,1);
    }

    /**
     * Añade cantidad al producto
     * @param cantidad cantidad a añadir al producto
     */
    public void anyadeCantidad(int cantidad){
        this.cantidad += cantidad;
    }
}
