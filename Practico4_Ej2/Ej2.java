import java.util.Random;

public class Ej2 {public static void main(String[] args) {
    Random random = new Random();

    // Generar un entero m al azar entre 1 y 100
    int m = random.nextInt(100) + 1;

    // Mostrar el valor generado
    System.out.println("Número generado (m): " + m);

    // Calcular la suma de todos los enteros entre 1 y m
    int suma = 0;
    for (int i = 1; i <= m; i++) {
        suma += i;
    }

    // Mostrar el resultado
    System.out.println("La suma de los enteros entre 1 y " + m + " es: " + suma);
}

}
