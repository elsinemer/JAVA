package sensores;

public abstract class Sensor {
    protected double valor;

    public double getValor() {
        return valor;
    }

    // Cada sensor define cómo mide (simula) su valor
    public abstract void medir();

    // Para mostrar la unidad en pantalla
    public abstract String unidad();

    // Nombre legible del sensor
    public abstract String nombre();
}
