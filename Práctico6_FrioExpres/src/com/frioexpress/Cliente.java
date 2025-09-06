package com.frioexpress;

import java.math.BigDecimal;
import java.util.Objects;

public class Cliente {
    private final String rut;
    private String razonSocial;
    private String direccionEntrega;
    private BigDecimal limiteCredito;
    private BigDecimal deudaActual;

    public Cliente(String rut, String razonSocial, String direccionEntrega, BigDecimal limiteCredito, BigDecimal deudaActual) {
        this.rut = Objects.requireNonNull(rut);
        this.razonSocial = Objects.requireNonNull(razonSocial);
        this.direccionEntrega = Objects.requireNonNull(direccionEntrega);
        this.limiteCredito = Objects.requireNonNull(limiteCredito);
        this.deudaActual = Objects.requireNonNull(deudaActual);
    }

    public String getRut() { return rut; }
    public String getRazonSocial() { return razonSocial; }
    public BigDecimal getLimiteCredito() { return limiteCredito; }
    public BigDecimal getDeudaActual() { return deudaActual; }
    public String getDireccionEntrega() { return direccionEntrega; }

    public BigDecimal creditoDisponible() {
        return limiteCredito.subtract(deudaActual);
    }

    public void aumentarDeuda(BigDecimal monto) {
        this.deudaActual = this.deudaActual.add(monto);
    }

    @Override
    public String toString() {
        return rut+" - "+razonSocial+" (crédito disp.: $"+creditoDisponible()+")";
    }
}
