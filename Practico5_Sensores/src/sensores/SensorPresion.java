package sensores;

import java.util.concurrent.ThreadLocalRandom;

public class SensorPresion extends Sensor {

    @Override
    public void medir() {
        // Simula una presión atmosférica entre 980 y 1050 hPa
        this.valor = ThreadLocalRandom.current().nextDouble(980.0, 1050.0);
    }

    @Override
    public String unidad() {
        return "hPa";
    }

    @Override
    public String nombre() {
        return "Sensor de Presión";
    }
}
