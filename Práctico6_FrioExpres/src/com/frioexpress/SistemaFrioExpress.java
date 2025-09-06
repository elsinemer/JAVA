package com.frioexpress;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class SistemaFrioExpress {
    private final Inventario inventario = new Inventario();
    private final Map<String, Cliente> clientes = new HashMap<>();
    private final LinkedList<Pedido> colaDespacho = new LinkedList<>();
    private long secPedido = 1L;

    public Inventario inventario() { return inventario; }

    public void altaCliente(Cliente c) { clientes.put(c.getRut(), c); }

    public void registrarIngreso(String codigo, double kg) throws ProductoNoCongeladoException {
        inventario.get(codigo).acreditarStock(kg);
    }

    public void registrarTemperatura(String codigo, double temp, LocalDateTime ts) throws ProductoNoCongeladoException {
        inventario.get(codigo).registrarTemperatura(temp, ts);
    }

    // Crea pedido y valida todo antes de debitar
    public Pedido procesarPedido(String rutCliente, Map<String, Double> itemsKg)
            throws ProductoNoCongeladoException, StockInsuficienteException,
            LimiteCreditoExcedidoException, CadenaFrioRotaException, StockMinimoAlcanzadoException {

        Cliente cli = clientes.get(rutCliente);
        if (cli == null) throw new IllegalArgumentException("Cliente inexistente: "+rutCliente);

        Pedido pedido = new Pedido(secPedido++, rutCliente);

        // 1) construir lineas con precios actuales
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, Double> e : itemsKg.entrySet()) {
            Producto p = inventario.get(e.getKey());
            double kg = e.getValue();
            // validaciones previas de disponibilidad y cadena
            if (kg > p.getStockKg()) throw new StockInsuficienteException("Stock insuficiente para "+p.getCodigo());
            if (p.isCadenaRota()) throw new CadenaFrioRotaException("Producto "+p.getCodigo()+" con cadena de frío rota");
            LineaPedido lp = new LineaPedido(p.getCodigo(), kg, p.getPrecioPorKg());
            pedido.agregarLinea(lp);
            total = total.add(lp.subtotal());
        }

        // 2) crédito
        if (total.compareTo(cli.creditoDisponible()) > 0) {
            throw new LimiteCreditoExcedidoException("Crédito insuficiente. Necesita $"+total+" y dispone de $"+cli.creditoDisponible());
        }

        // 3) debitar stocks (posible excepción de mínimo, se considera parte del flujo)
        for (LineaPedido lp : pedido.getLineas()) {
            try {
                inventario.get(lp.getProductoCodigo()).debitarStock(lp.getKgSolicitados());
            } catch (StockMinimoAlcanzadoException e) {
                // Re-lanzamos para que la capa superior informe pero dejamos consistentes los demás cambios
                throw e;
            }
        }

        // 4) deuda y encolar
        cli.aumentarDeuda(pedido.getTotal());
        colaDespacho.add(pedido);
        return pedido;
    }

    public List<Pedido> pedidosPendientes() { return Collections.unmodifiableList(colaDespacho); }
}
