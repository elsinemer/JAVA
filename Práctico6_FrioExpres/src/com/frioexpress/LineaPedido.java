package com.frioexpress;

import java.math.BigDecimal;

public class LineaPedido {
    private final String productoCodigo;
    private final double kgSolicitados;
    private final BigDecimal precioUnitario;

    public LineaPedido(String productoCodigo, double kgSolicitados, BigDecimal precioUnitario) {
        this.productoCodigo = productoCodigo;
        this.kgSolicitados = kgSolicitados;
        this.precioUnitario = precioUnitario;
    }

    public String getProductoCodigo() { return productoCodigo; }
    public double getKgSolicitados() { return kgSolicitados; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }

    public BigDecimal subtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(kgSolicitados));
    }

    @Override
    public String toString() {
        return productoCodigo+" x "+kgSolicitados+"kg = $"+subtotal();
    }
}
