package com.frioexpress;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        try {
            SistemaFrioExpress s = seed();

            // Caso 1: Pedido exitoso
            Map<String, Double> compra1 = new HashMap<>();
            compra1.put("CAR001", 10.0); // carne
            compra1.put("VEG001", 5.0);  // vegetal
            Pedido p1 = s.procesarPedido("111111110019", compra1);
            System.out.println("OK: " + p1);

            // Caso 2: Excede stock
            try {
                Map<String, Double> compra2 = new HashMap<>();
                compra2.put("PES001", 1000.0); // exagerado
                s.procesarPedido("111111110019", compra2);
            } catch (Exception ex) {
                System.out.println("Esperado (excede stock): " + ex.getMessage());
            }

            // Caso 3: Excede crédito
            try {
                Map<String, Double> compra3 = new HashMap<>();
                compra3.put("HEL001", 50.0); // caro para el cliente de poco crédito
                s.procesarPedido("222222220019", compra3);
            } catch (Exception ex) {
                System.out.println("Esperado (excede crédito): " + ex.getMessage());
            }

            // Caso 4: Cadena de frío rota
            try {
                // Simulamos lecturas fuera de rango por >30 min
                s.registrarTemperatura("VEG002", -10, LocalDateTime.now());
                s.registrarTemperatura("VEG002", -10, LocalDateTime.now().plusMinutes(20));
                s.registrarTemperatura("VEG002", -10, LocalDateTime.now().plusMinutes(45)); // acumula 45 min
                Map<String, Double> compra4 = new HashMap<>();
                compra4.put("VEG002", 1.0);
                s.procesarPedido("111111110019", compra4);
            } catch (Exception ex) {
                System.out.println("Esperado (cadena rota): " + ex.getMessage());
            }

            // Caso 5: Reporte bajo mínimo
            List<Producto> bajoMin = s.inventario().bajoMinimo();
            System.out.println("Bajo mínimo:");
            for (Producto p : bajoMin) System.out.println(" - " + p);

            // Caso 6: Código inexistente en ingreso/lookup
            try {
                s.registrarIngreso("NO_EXISTE", 5);
            } catch (Exception ex) {
                System.out.println("Esperado (código inexistente): " + ex.getMessage());
            }

            // Listar pendientes
            System.out.println("Pendientes de despacho: " + s.pedidosPendientes().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SistemaFrioExpress seed() throws Exception {
        SistemaFrioExpress s = new SistemaFrioExpress();

        // Productos (8)
        s.inventario().altaProducto(new Producto("CAR001", "Carne vacuna premium", Categoria.CARNES, -18, 200, 50, new BigDecimal("350.00")));
        s.inventario().altaProducto(new Producto("CAR002", "Carne de cerdo", Categoria.CARNES, -18, 60, 50, new BigDecimal("250.00")));
        s.inventario().altaProducto(new Producto("PES001", "Merluza", Categoria.PESCADOS, -18, 80, 30, new BigDecimal("420.00")));
        s.inventario().altaProducto(new Producto("PES002", "Salmón", Categoria.PESCADOS, -18, 40, 20, new BigDecimal("990.00")));
        s.inventario().altaProducto(new Producto("VEG001", "Arvejas", Categoria.VEGETALES, -18, 30, 25, new BigDecimal("120.00")));
        s.inventario().altaProducto(new Producto("VEG002", "Zanahorias", Categoria.VEGETALES, -18, 26, 25, new BigDecimal("110.00"))); // cerca del mínimo
        s.inventario().altaProducto(new Producto("HEL001", "Helado crema", Categoria.HELADOS, -18, 70, 20, new BigDecimal("650.00")));
        s.inventario().altaProducto(new Producto("HEL002", "Helado chocolate", Categoria.HELADOS, -18, 22, 20, new BigDecimal("680.00"))); // cerca del mínimo

        // Clientes (4)
        s.altaCliente(new Cliente("111111110019", "Super Uno", "Depósito Centro", new BigDecimal("50000"), new BigDecimal("0")));
        s.altaCliente(new Cliente("222222220019", "Resto Dos", "Av. Italia 1234", new BigDecimal("5000"), new BigDecimal("0"))); // poco crédito
        s.altaCliente(new Cliente("333333330019", "Tienda Tres", "18 de Julio 555", new BigDecimal("15000"), new BigDecimal("1000")));
        s.altaCliente(new Cliente("444444440019", "Super Cuatro", "Camino Maldonado", new BigDecimal("80000"), new BigDecimal("20000")));

        return s;
    }
}
