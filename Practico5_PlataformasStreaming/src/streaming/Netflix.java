package streaming;

public class Netflix extends PlataformaStreaming {

    public Netflix() {
        super("Netflix");
    }

    @Override
    public void reproducirContenido(String titulo) {
        // Simula autoreproducción con prebuffer y perfiles
        System.out.println(nombre + " reproduce la serie/película \"" 
                           + titulo + "\" en 4K con perfiles y subtítulos.");
    }
}
