package personajes;

public class Guerrero extends Personaje {
    public Guerrero(String nombre, int nivel) {
        super(nombre, nivel);
    }

    @Override
    public void accionEspecial() {
        System.out.println(nombre + " realiza un ataque con espada (Nivel " + nivel + ")");
    }
}
