// encoding: UTF-8
package app;

import carrera.*;

/**
 * Simulación de carrera con 5 rondas usando un arreglo polimórfico.
 */
public class Main {
    public static void main(String[] args) {
        Vehiculo[] competidores = new Vehiculo[] {
            new Auto("Auto 1"),
            new Moto("Moto 1"),
            new Auto("Auto 2"),
            new Moto("Moto 2")
        };

        int rondas = 5;
        System.out.println(">>> Carrera de Vehículos - " + rondas + " rondas <<<\n");

        for (int r = 1; r <= rondas; r++) {
            System.out.println("— Ronda " + r + " —");
            for (Vehiculo v : competidores) {
                int delta = v.avanzar();
                System.out.printf("  %s avanza %d u.  (total: %d)%n",
                        v.getNombre(), delta, v.getDistanciaAcumulada());
            }
            System.out.println();
        }

        // Determinar ganador (puede haber empate)
        int max = -1;
        for (Vehiculo v : competidores) {
            if (v.getDistanciaAcumulada() > max) {
                max = v.getDistanciaAcumulada();
            }
        }

        System.out.println(">>> Resultados finales <<<");
        for (Vehiculo v : competidores) {
            System.out.printf("  %s total: %d%n", v.getNombre(), v.getDistanciaAcumulada());
        }

        System.out.print("\nGanador(es): ");
        boolean primero = true;
        for (Vehiculo v : competidores) {
            if (v.getDistanciaAcumulada() == max) {
                if (!primero) System.out.print(", ");
                System.out.print(v.getNombre());
                primero = false;
            }
        }
        System.out.println(" (con " + max + " unidades).");
    }
}
