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
    CAMISETAS("Camisetas"),
    NEGOCIOS("Negocios"),
    OTROS("Otros");

    private final String nombre;
    private final byte prioridad;


    /**
     * Devuelve el nombre de la categoria bien escrito
     * @return nombre de la categoria bien escrito
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la prioridad de la categoria
     * @return prioridad de la categoria
     */
    public byte getPrioridad() {
        return prioridad;
    }

    /**
     * Crea una nueva categoria de produto
     * @param nombre nombre de la categoria
     * @param prioridad prioridad de la categoria
     */
    CategoriaProducto(String nombre, byte prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    /**
     * Crea una nueva categoria con la prioridad por defecto
     * @param nombre nombre de la categoria
     */
    CategoriaProducto(String nombre){
        this(nombre,(byte) 100);
    }

    /**
     * Devuelve el nombre de la categoria bien escrita
     * @return nombre de la categoria bien escrita
     */
    @Override
    public String toString() {
        return nombre;
    }
}
