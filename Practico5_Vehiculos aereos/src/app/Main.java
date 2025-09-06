package app;

import aereo.*;

public class Main {
    public static void main(String[] args) {
        // Arreglo polimórfico con diferentes vehículos aéreos
        VehiculoAereo[] colaDespegue = new VehiculoAereo[] {
            new Avion("Boeing 737"),
            new Helicoptero("Bell 206"),
            new Avion("Airbus A320"),
            new Helicoptero("UH-60 Black Hawk")
        };

        System.out.println(">>> Orden de despegue según posición en el arreglo <<<\n");
        for (int i = 0; i < colaDespegue.length; i++) {
            System.out.println("Orden #" + (i + 1) + ":");
            colaDespegue[i].despegar(); // polimorfismo
            System.out.println();
        }
    }
}
