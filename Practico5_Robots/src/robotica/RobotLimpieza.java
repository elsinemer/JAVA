package robotica;

public class RobotLimpieza extends Robot {

    public RobotLimpieza(String nombre) {
        super(nombre);
    }

    @Override
    public void realizarTarea() {
        System.out.println(nombre + " (Limpieza) aspira, barre y friega el piso.");
    }
}
