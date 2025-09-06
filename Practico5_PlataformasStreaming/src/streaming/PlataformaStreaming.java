package streaming;

public abstract class PlataformaStreaming {
    protected String nombre;

    public PlataformaStreaming(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    // Cada plataforma decide cómo reproduce el contenido
    public abstract void reproducirContenido(String titulo);
}
