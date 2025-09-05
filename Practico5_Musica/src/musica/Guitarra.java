package musica;

public class Guitarra extends InstrumentoMusical {
    public Guitarra(String nombre) {
        super(nombre);
    }

    @Override
    public void tocarNota(String nota) {
        // Simula el sonido de cuerda
        System.out.println(nombre + " (Guitarra) toca la nota " + nota );
    }
}
