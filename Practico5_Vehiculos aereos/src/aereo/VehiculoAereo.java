package aereo;

/**
 * Ejercicio 13
 * Clase base abstracta para vehículos aéreos.
 */
public abstract class VehiculoAereo {
    protected String modelo;

    public VehiculoAereo(String modelo) {
        this.modelo = modelo;
    }

    public String getModelo() { return modelo; }

    /** Cada vehículo define su forma de despegar */
    public abstract void despegar();
}
