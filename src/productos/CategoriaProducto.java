package productos;

import java.io.Serializable;

/**
 * Enum para las categorias de productos.
 * @author Rafael Niñoles Parra
 */
public enum CategoriaProducto implements Serializable {
    MAS_USADO("Más usados",(byte) 1),
    COPIAS("Copias"),
    FOTOGRAFIA("Fotografia"),
    MARCOS("Marcos"),
    CAMISETAS("Camisetas"),
    OTROS("Otros");

    private final String nombre;
    private byte prioridad;

    CategoriaProducto(String nombre, byte prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    CategoriaProducto(String nombre){
        this(nombre,(byte) 100);
    }

    public String getNombre() {
        return nombre;
    }

    public byte getPrioridad() {
        return prioridad;
    }

    /**
     * Devuelve el nombre de la categoria
     * @return nombre de la categoria
     */
    @Override
    public String toString() {
        return nombre;
    }
}
