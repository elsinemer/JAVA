
# FríoExpress (Eclipse Project)

Proyecto Java listo para importar en Eclipse (**File > Import > Existing Projects into Workspace**).

- **Codificación**: UTF-8 (definida en `.settings/org.eclipse.core.resources.prefs`).
- **Estructuras**: `TreeMap` (productos), `LinkedList` (cola de pedidos), `HashMap` opcional (índice por categoría).
- **Moneda**: `BigDecimal`.
- **Tiempo**: `java.time`.

## Ejecutar
Abrí `com.frioexpress.Demo` y corré `main`. Muestra todos los **casos de prueba obligatorios**.

## Paquetes y clases
- `com.frioexpress`:
  - Dominio: `Categoria`, `Producto`, `Cliente`, `LineaPedido`, `Pedido`, `PedidoEstado`, `Inventario`
  - Fachada: `SistemaFrioExpress`
  - Excepciones: `StockInsuficienteException`, `CadenaFrioRotaException`, `LimiteCreditoExcedidoException`, `ProductoNoCongeladoException`, `StockMinimoAlcanzadoException`
  - Demo: `Demo`

