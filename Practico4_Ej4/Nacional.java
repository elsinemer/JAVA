public class Nacional extends Articulo {
    private String departamento;
    private boolean subsidiado;

    // Constructor
    public Nacional(int codigo, String nombre, float precioCosto, String departamento, boolean subsidiado) {
        super(codigo, nombre, precioCosto);
        this.departamento = departamento;
        this.subsidiado = subsidiado;
    }

    // Getters y Setters
    public String getDepartamento() {
        return departamento;
    }

    public boolean getSubsidado() {
        return subsidiado;
    }

    public void setSubsidado(boolean subsidiado) {
        this.subsidiado = subsidiado;
    }

    // Cálculo del precio de venta
    @Override
    public float precioVenta() {
        float precioInicial = super.precioVenta();

        if (subsidiado) {
            return precioInicial; // mantiene el precio inicial
        } else {
            if (departamento.equalsIgnoreCase("Montevideo")) {
                return precioInicial * 1.15f; // +15% si es Montevideo
            } else {
                return precioInicial * 1.10f; // +10% si es del interior
            }
        }
    }

    @Override
    public String tipoArticulo() {
        return "Nacional";
    }
}
