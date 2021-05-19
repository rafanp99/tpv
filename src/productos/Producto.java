package productos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Producto implements Serializable {
    private String nombre;
    private int precioCentimos;
    private String uriImagen;
    private Set<CategoriaProducto> categorias;

    public Producto(String nombre, int precioCentimos, String uriImagen, CategoriaProducto categoria) {
        this.nombre = nombre;
        this.precioCentimos = precioCentimos;
        this.uriImagen = uriImagen;
        this.categorias = new HashSet<>();
        categorias.add(categoria);
    }

    public Producto(String nombre, int precioCentimos, String uriImagen, Set<CategoriaProducto> categorias) {
        this.nombre = nombre;
        this.precioCentimos = precioCentimos;
        this.uriImagen = uriImagen;
        this.categorias = new HashSet<>(categorias);
    }

    public Producto(String nombre, int precioCentimos, String uriImagen) {
        this(nombre,precioCentimos,uriImagen,CategoriaProducto.OTROS);
    }

    public Producto anyadeCategoria(CategoriaProducto categoria){
        categorias.add(categoria);
        return this;
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

    public HashSet<CategoriaProducto> getCategoria() {
        return new HashSet<>(categorias);
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
}
