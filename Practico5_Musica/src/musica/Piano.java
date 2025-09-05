package musica;

public class Piano extends InstrumentoMusical {
    public Piano(String nombre) {
        super(nombre);
    }

    @Override
    public void tocarNota(String nota) {
        // Simula pulsación de tecla
        System.out.println(nombre + " (Piano) presiona la tecla de " + nota );
    }
}
