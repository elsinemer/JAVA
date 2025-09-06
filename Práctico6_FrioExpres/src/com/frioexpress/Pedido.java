package com.frioexpress;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final long id;
    private final String clienteRut;
    private final List<LineaPedido> lineas = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;
    private PedidoEstado estado = PedidoEstado.PENDIENTE;
    private final LocalDateTime fecha;

    public Pedido(long id, String clienteRut) {
        this.id = id;
        this.clienteRut = clienteRut;
        this.fecha = LocalDateTime.now();
    }

    public long getId() { return id; }
    public String getClienteRut() { return clienteRut; }
    public List<LineaPedido> getLineas() { return lineas; }
    public BigDecimal getTotal() { return total; }
    public PedidoEstado getEstado() { return estado; }
    public void setEstado(PedidoEstado e) { this.estado = e; }

    public void agregarLinea(LineaPedido lp) {
        this.lineas.add(lp);
        this.total = this.total.add(lp.subtotal());
    }

    @Override
    public String toString() {
        return "Pedido #"+id+" ("+estado+") total=$"+total+" lineas="+lineas.size();
    }

	public LocalDateTime getFecha() {
		return fecha;
	}
}
