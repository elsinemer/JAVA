package app;

import sensores.*;

public class Main {
    public static void main(String[] args) {
        // Arreglo polimórfico de sensores
        Sensor[] sensores = new Sensor[] {
            new SensorTemperatura(),
            new SensorPresion(),
            new SensorTemperatura(),
            new SensorPresion()
        };

        System.out.println(">>> Lecturas simuladas de sensores <<<\n");

        // Realiza 5 ciclos de medición
        for (int ciclo = 1; ciclo <= 5; ciclo++) {
            System.out.println("Ciclo " + ciclo + ":");
            for (Sensor s : sensores) {
                s.medir(); // cada sensor genera su propio valor
                System.out.printf("  - %s: %.2f %s%n", s.nombre(), s.getValor(), s.unidad());
            }
            System.out.println();
        }
    }
}
