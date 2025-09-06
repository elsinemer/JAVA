package streaming;

public class YouTube extends PlataformaStreaming {

    public YouTube() {
        super("YouTube");
    }

    @Override
    public void reproducirContenido(String titulo) {
        // Simula reproducción con anuncios y calidad adaptativa
    	System.out.println(nombre + " reproduce el video \"" + titulo + "\" con calidad adaptativa y posibles anuncios.");
    }
}
