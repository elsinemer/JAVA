// encoding: UTF-8
package carrera;

/**
 * Moto: avanza entre 6 y 22 unidades por turno (más ágil, más variable).
 */
public class Moto extends Vehiculo {

    public Moto(String nombre) { super(nombre); }

    @Override
    public int avanzar() {
        int delta = rnd(6, 22);
        acumular(delta);
        return delta;
    }
}
