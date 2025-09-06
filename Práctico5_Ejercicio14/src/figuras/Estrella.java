// encoding: UTF-8
package figuras;

/**
 * Estrella: ejemplo de subclase de Figura.
 */
public class Estrella extends Figura {
    private int puntas;

    public Estrella(String nombre, int puntas) {
        super(nombre);
        this.puntas = puntas;
    }

    public int getPuntas() { return puntas; }
}
