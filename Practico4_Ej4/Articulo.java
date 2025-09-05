// Clase base Articulo
public class Articulo {
    private int codigo;
    private String nombre;
    private float precioCosto;

    // Constructor
    public Articulo(int codigo, String nombre, float precioCosto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioCosto = precioCosto;
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(float precioCosto) {
        this.precioCosto = precioCosto;
    }

    // Precio de venta inicial (costo + 20%)
    public float precioVenta() {
        return precioCosto * 1.20f;
    }

    // Tipo de artículo (a redefinir en las subclases)
    public String tipoArticulo() {
        return "Artículo genérico";
    }
}
