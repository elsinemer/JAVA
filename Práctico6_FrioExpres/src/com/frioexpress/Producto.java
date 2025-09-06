package com.frioexpress;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Producto implements Comparable<Producto> {
    private final String codigo;
    private String nombre;
    private Categoria categoria;
    private double tempRequerida; // °C (negativo)
    private double stockKg;
    private double stockMinKg;
    private BigDecimal precioPorKg;

    // Control de cadena de frío
    private LocalDateTime ultimaLectura;
    private boolean fueraDeRango; // si la última lectura está fuera de rango
    private long minutosFueraDeRangoAcumulados; // acumulado
    private boolean cadenaRota; // bandera final

    public Producto(String codigo, String nombre, Categoria categoria, double tempRequerida,
                    double stockKg, double stockMinKg, BigDecimal precioPorKg) {
        this.codigo = Objects.requireNonNull(codigo);
        this.nombre = Objects.requireNonNull(nombre);
        this.categoria = Objects.requireNonNull(categoria);
        this.tempRequerida = tempRequerida;
        this.stockKg = stockKg;
        this.stockMinKg = stockMinKg;
        this.precioPorKg = precioPorKg;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Categoria getCategoria() { return categoria; }
    public double getTempRequerida() { return tempRequerida; }
    public double getStockKg() { return stockKg; }
    public double getStockMinKg() { return stockMinKg; }
    public BigDecimal getPrecioPorKg() { return precioPorKg; }
    public boolean isCadenaRota() { return cadenaRota; }
    public long getMinutosFueraDeRangoAcumulados() { return minutosFueraDeRangoAcumulados; }

    public void acreditarStock(double kg) {
        if (kg < 0) throw new IllegalArgumentException("kg negativo");
        stockKg += kg;
    }

    public void debitarStock(double kg) throws StockInsuficienteException, StockMinimoAlcanzadoException, CadenaFrioRotaException {
        if (kg < 0) throw new IllegalArgumentException("kg negativo");
        if (cadenaRota) throw new CadenaFrioRotaException("Producto "+codigo+" con cadena de frío rota.");
        if (kg > stockKg) throw new StockInsuficienteException("Stock insuficiente para "+codigo);
        stockKg -= kg;
        if (stockKg <= stockMinKg) {
            throw new StockMinimoAlcanzadoException("Stock mínimo alcanzado para "+codigo+" (restan "+stockKg+" kg).");
        }
    }

    public void registrarTemperatura(double tempActual, LocalDateTime timestamp) {
        // Si no hay lectura previa, solo inicializamos
        if (ultimaLectura == null) {
            ultimaLectura = timestamp;
            fueraDeRango = tempActual > tempRequerida;
            return;
        }
        // calcular delta desde última lectura
        long minutos = Duration.between(ultimaLectura, timestamp).toMinutes();
        if (minutos < 0) minutos = 0;
        boolean ahoraFuera = tempActual > tempRequerida;
        // si venía fuera de rango, el tiempo transcurrido se suma
        if (fueraDeRango) {
            minutosFueraDeRangoAcumulados += minutos;
            if (minutosFueraDeRangoAcumulados > 30) cadenaRota = true;
        }
        // actualizar estado para la próxima
        ultimaLectura = timestamp;
        fueraDeRango = ahoraFuera;
    }

    @Override
    public int compareTo(Producto o) {
        return this.codigo.compareTo(o.codigo);
    }

    @Override
    public String toString() {
        return codigo+" - "+nombre+" ["+categoria+"] "+stockKg+"kg @ $"+precioPorKg+"/kg "+(cadenaRota?"(CADENA ROTA)":"");
    }
}
