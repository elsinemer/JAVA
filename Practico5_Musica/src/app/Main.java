package app;

import musica.*;
import java.util.Random;

public class Main {
    private static final String[] NOTAS = {"Do", "Re", "Mi", "Fa", "Sol", "La", "Si"};

    private static String notaAleatoria(Random r) {
        return NOTAS[r.nextInt(NOTAS.length)];
    }

    public static void main(String[] args) {
        // Arreglo polimórfico con distintos instrumentos
        InstrumentoMusical[] banda = new InstrumentoMusical[] {
            new Guitarra("Strato"),
            new Piano("Yamaha"),
            new Guitarra("Les Paul"),
            new Piano("Kawai")
        };

        Random r = new Random();
        int cantidadNotas = 10; // longitud de la secuencia

        System.out.println(">>> Secuencia de notas aleatorias (" + cantidadNotas + ") <<<\n");

        for (int i = 0; i < cantidadNotas; i++) {
            String nota = notaAleatoria(r);
            // Cada instrumento toca la MISMA nota pero con comportamiento distinto
            for (InstrumentoMusical inst : banda) {
                inst.tocarNota(nota);
            }
            System.out.println(); // separación visual entre compases
        }
    }
}
