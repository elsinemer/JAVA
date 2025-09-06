// encoding: UTF-8
package app;

import figuras.*;

/**
 * Ejercicio 14: crear un arreglo de Figura y, usando instanceof,
 * contar cuántos elementos son Poligono y cuántos son Estrella.
 */
public class Main {
    public static void main(String[] args) {
        Figura[] figuras = new Figura[] {
            new Poligono("Triangulo", 3),
            new Estrella("Estrella de 5 puntas", 5),
            new Poligono("Cuadrado", 4),
            new Estrella("Estrella de 8 puntas", 8),
            new Poligono("Pentagono", 5)
        };

        int cantPoligonos = 0;
        int cantEstrellas = 0;

        System.out.println(">>> Listado de figuras <<<\n");
        for (Figura f : figuras) {
            if (f instanceof Poligono) {
                cantPoligonos++;
                Poligono p = (Poligono) f;
                System.out.println("- Poligono: " + p.getNombre() + " (" + p.getLados() + " lados)");
            } else if (f instanceof Estrella) {
                cantEstrellas++;
                Estrella e = (Estrella) f;
                System.out.println("- Estrella: " + e.getNombre() + " (" + e.getPuntas() + " puntas)");
            }
        }

        System.out.println("\nResumen:");
        System.out.println("Poligonos: " + cantPoligonos);
        System.out.println("Estrellas: " + cantEstrellas);
    }
}
