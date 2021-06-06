package productos;

import logger.TPVLogger;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Clase que un producto con sus caracteristicas basicas
 * @author Rafael Niñoles Parra
 */
public class Producto implements Serializable {
    private static final Logger LOGGER = TPVLogger.getLogger();
    private final String nombre;
    private final int precioCentimos;
    private final String uriImagen;
    private final Set<CategoriaProducto> categorias;

    /**
     * Crea un nuevo producto
     * @param nombre Nombre del producto
     * @param precioCentimos Precio del producto en centimos
     * @param uriImagen Uri de la imagen del producto
     * @param categoria Categoria del producto
     */
    public Producto(String nombre, int precioCentimos, String uriImagen, CategoriaProducto categoria) {
        this.nombre = nombre;
        this.precioCentimos = precioCentimos;
        this.uriImagen = uriImagen;
        this.categorias = new HashSet<>();
        categorias.add(categoria);
    }

    /**
     * Crea un nuevo producto con varias categorias
     * @param nombre Nombre del producto
     * @param precioCentimos Precio del producto en centimos
     * @param uriImagen Uri de la imagen del producto
     * @param categorias Conjunto de categorias del producto
     */
    public Producto(String nombre, int precioCentimos, String uriImagen, Set<CategoriaProducto> categorias) {
        this.nombre = nombre;
        this.precioCentimos = precioCentimos;
        this.uriImagen = uriImagen;
        this.categorias = new HashSet<>(categorias);
    }

    /**
     * Crea un nuevo producto con la categoria OTROS
     * @param nombre Nombre del producto
     * @param precioCentimos Precio en centimos del producto
     * @param uriImagen Uri de la imagen del producto
     */
    public Producto(String nombre, int precioCentimos, String uriImagen) {
        this(nombre,precioCentimos,uriImagen,CategoriaProducto.OTROS);
    }

    /**
     * Devuelve el nombre del producto
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el precio del producto en centimos
     * @return precio del producto en centimos
     */
    public int getPrecioCentimos() {
        return precioCentimos;
    }

    /**
     * Devuelve una cadena de texto con el precio formateado en euros con dos decimales
     * @return cadena de texto con el precio formateado en euros con dos decimales
     */
    public String getPrecioDecimal(){
        return String.format("%.2f",(double) precioCentimos/100);
    }

    /**
     * Devuelve la URI de la imagen del producto
     * @return URI de la imagen del producto
     */
    public String getUriImagen() {
        return uriImagen;
    }

    /**
     * Devuelve el conjunto de categorias que tiene el producto
     * @return conjunto de categorias que tiene el producto
     */
    public HashSet<CategoriaProducto> getCategorias() {
        return new HashSet<>(categorias);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return precioCentimos == producto.precioCentimos &&
                Objects.equals(nombre, producto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, precioCentimos);
    }
}
