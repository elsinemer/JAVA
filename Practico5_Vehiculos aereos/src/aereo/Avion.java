package aereo;

public class Avion extends VehiculoAereo {
    public Avion(String modelo) { super(modelo); }

    @Override
    public void despegar() {
        System.out.println("Avión " + modelo + " acelera por la pista y despega.");
    }
}
