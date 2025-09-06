// encoding: UTF-8
package figuras;

/**
 * Polígono: ejemplo de subclase de Figura.
 */
public class Poligono extends Figura {
    private int lados;

    public Poligono(String nombre, int lados) {
        super(nombre);
        this.lados = lados;
    }

    public int getLados() { return lados; }
}
