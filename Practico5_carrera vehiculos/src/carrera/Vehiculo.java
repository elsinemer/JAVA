// encoding: UTF-8
package carrera;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Vehiculo (abstracto) para la carrera.
 * Cada vehículo define su forma de avanzar en un turno.
 */
public abstract class Vehiculo {
    protected String nombre;
    protected int distanciaAcumulada = 0;

    public Vehiculo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public int getDistanciaAcumulada() { return distanciaAcumulada; }

    /** Avanza y devuelve la distancia recorrida en este turno */
    public abstract int avanzar();

    /** Acumula la distancia recorrida */
    protected void acumular(int d) { this.distanciaAcumulada += d; }

    /** Utilidad para obtener un entero aleatorio inclusivo */
    protected int rnd(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
