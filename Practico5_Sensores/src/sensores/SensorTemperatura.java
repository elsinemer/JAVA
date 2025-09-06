package sensores;

import java.util.concurrent.ThreadLocalRandom;

public class SensorTemperatura extends Sensor {

    @Override
    public void medir() {
        // Simula una temperatura entre 15.0 y 35.0 °C
        this.valor = ThreadLocalRandom.current().nextDouble(15.0, 35.0);
    }

    @Override
    public String unidad() {
        return "°C";
    }

    @Override
    public String nombre() {
        return "Sensor de Temperatura";
    }
}
