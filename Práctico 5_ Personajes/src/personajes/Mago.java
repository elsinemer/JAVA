package personajes;

public class Mago extends Personaje {
    public Mago(String nombre, int nivel) {
        super(nombre, nivel);
    }

    @Override
    public void accionEspecial() {
        System.out.println(nombre + " lanza un hechizo de fuego (Nivel " + nivel + ")");
    }
}
