import java.util.Random;

public class Ej_3 {
    public static void main(String[] args) {
        Random random = new Random();
        int sumaImpares = 0;

        System.out.println("Generando números aleatorios entre 0 y 10...\n");

        // Continuar generando hasta que la suma de impares supere 25
        while (sumaImpares <= 25) {
            int num = random.nextInt(11); // Genera un número entre 0 y 10

            // Verificar si es par o impar y mostrar en pantalla
            if (num % 2 == 0) {
                System.out.println(num + " es PAR");
            } else {
                System.out.println(num + " es IMPAR");
                sumaImpares += num; // Sumar solo si es impar
            }
        }

        // Mostrar la suma final de los impares
        System.out.println("\nLa suma de los números impares superó 25.");
        System.out.println("Suma final = " + sumaImpares);
    }

}
