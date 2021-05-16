package productos;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Producto implements Serializable,Comparable {
    private String nombre;
    private int precioCentimos;
    private String uriImagen;
    private CategoriaProducto categoria;

    public Producto(String nombre, int precioCentimos, String uriImagen, CategoriaProducto categoria) {
        this.nombre = nombre;
        this.precioCentimos = precioCentimos;
        this.uriImagen = uriImagen;
        this.categoria = categoria;
    }

    public Producto(String nombre, int precioCentimos, String uriImagen) {
        this(nombre,precioCentimos,uriImagen,CategoriaProducto.OTROS);
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecioCentimos() {
        return precioCentimos;
    }

    public double getPrecio(){
        return (double) precioCentimos/100;
    }

    public String getUriImagen() {
        return uriImagen;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return nombre.equals(producto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public int compareTo(Object o) {
        Producto otro = (Producto) o;
        return this.getCategoria().compareTo(((Producto) o).getCategoria());
    }
}
