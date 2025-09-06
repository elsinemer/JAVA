// encoding: UTF-8
package carrera;

/**
 * Auto: avanza entre 8 y 20 unidades por turno.
 */
public class Auto extends Vehiculo {

    public Auto(String nombre) { super(nombre); }

    @Override
    public int avanzar() {
        int delta = rnd(8, 20);
        acumular(delta);
        return delta;
    }
}
