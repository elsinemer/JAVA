package robotica;

public abstract class Robot {
    protected String nombre;

    public Robot(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    // Cada robot define su forma de realizar la tarea
    public abstract void realizarTarea();
}
