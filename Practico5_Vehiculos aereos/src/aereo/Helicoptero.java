package aereo;

public class Helicoptero extends VehiculoAereo {
    public Helicoptero(String modelo) { super(modelo); }

    @Override
    public void despegar() {
        System.out.println("Helicóptero " + modelo + " eleva verticalmente y despega.");
    }
}
