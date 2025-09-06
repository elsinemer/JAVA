package robotica;

public class RobotCocina extends Robot {

    public RobotCocina(String nombre) {
        super(nombre);
    }

    @Override
    public void realizarTarea() {
        System.out.println(nombre + " (Cocina) prepara una comida saludable y lava los utensilios.");
    }
}
