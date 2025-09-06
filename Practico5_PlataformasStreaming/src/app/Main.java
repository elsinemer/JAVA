package app;

import streaming.*;

public class Main {
    public static void main(String[] args) {
        // Arreglo polimórfico de plataformas
        PlataformaStreaming[] plataformas = new PlataformaStreaming[] {
            new Netflix(),
            new YouTube()
        };

        // 3 contenidos para cada plataforma
        String[] contenidosNetflix = {"Stranger Things", "The Irishman", "Dark"};
        String[] contenidosYouTube  = {"Curso Java en 10 min", "Documental de océanos", "Música LoFi"};

        System.out.println(">>> Reproducciones por plataforma <<<\n");

        for (PlataformaStreaming p : plataformas) {
            System.out.println("Plataforma: " + p.getNombre());
            String[] lista = (p instanceof Netflix) ? contenidosNetflix : contenidosYouTube;

            for (int i = 0; i < 3; i++) {
                p.reproducirContenido(lista[i]);
            }
            System.out.println();
        }
    }
}
