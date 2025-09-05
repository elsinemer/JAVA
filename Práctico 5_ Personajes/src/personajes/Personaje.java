package personajes;

public abstract class Personaje {
    protected String nombre;
    protected int nivel;

    public Personaje(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }

    public abstract void accionEspecial();
}
