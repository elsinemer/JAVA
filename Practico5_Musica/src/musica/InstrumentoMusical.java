package musica;

public abstract class InstrumentoMusical {
    protected String nombre;

    public InstrumentoMusical(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    // Método abstracto: cada instrumento interpreta la nota a su manera
    public abstract void tocarNota(String nota);
}
