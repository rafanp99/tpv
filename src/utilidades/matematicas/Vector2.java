package utilidades.matematicas;

/**
 * Clase auxiliar de ambito matematico para guardar un vector de dos dimensiones
 */
public class Vector2 {
    private int x;
    private int y;

    /**
     * Crea un nuevo vector de 2 dimensiones
     * @param x dimension x
     * @param y dimension y
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Devuelve la dimension X
     * @return dimension X
     */
    public int getX() {
        return x;
    }

    /**
     * Aplica nuevo valor a dimension X
     * @param x nueva dimension X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Devuelve la dimension y
     * @return dimension y
     */
    public int getY() {
        return y;
    }

    /**
     * Aplica nuevo valor a dimension y
     * @param y nueva dimension y
     */
    public void setY(int y) {
        this.y = y;
    }
}
