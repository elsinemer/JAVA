// encoding: UTF-8
package figuras;

/**
 * Figura (abstracta): clase base para el ejercicio 14.
 * Se incluye un nombre solo a efectos ilustrativos.
 */
public abstract class Figura {
    protected String nombre;

    public Figura(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
}
