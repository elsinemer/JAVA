package app;

import robotica.*;

public class Main {
    public static void main(String[] args) {
        // Arreglo polimórfico de robots
        Robot[] robots = new Robot[] {
            new RobotLimpieza("L-100"),
            new RobotCocina("C-200"),
            new RobotLimpieza("L-300"),
            new RobotCocina("C-400")
        };

        System.out.println(">>> Ejecución de tareas de robots <<<\n");

        for (Robot r : robots) {
            r.realizarTarea(); // polimorfismo
        }
    }
}
