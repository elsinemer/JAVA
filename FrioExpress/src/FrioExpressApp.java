import java.time.LocalDateTime;
import java.util.*;

/**
 * Sistema de Gestión para distribuidora de productos congelados "FríoExpress"
 * Requisitos: TreeMap para productos, cola de pedidos pendientes, excepciones personalizadas,
 * reportes e iteración, control de crédito, stock y cadena de frío.
 *
 * Ejecutar con:  javac FrioExpress.java && java FrioExpressApp
 */

// ======================== MODELO Y EXCEPCIONES ========================

enum Categoria {
    CARNES, PESCADOS, VEGETALES, HELADOS
}

class Producto {
    private final String codigo;            // único
    private String nombre;
    private Categoria categoria;
    private double tempRequeridaC;          // -18°C típicamente
    private double stockKg;                 // stock actual (kg)
    private double stockMinKg;              // stock mínimo de seguridad (kg)
    private double precioPorKg;
    private boolean cadenaFrioRota;         // bandera si hubo ruptura

    public Producto(String codigo, String nombre, Categoria categoria,
                    double tempRequeridaC, double stockKg, double stockMinKg, double precioPorKg) {
        this.codigo = Objects.requireNonNull(codigo);
        this.nombre = Objects.requireNonNull(nombre);
        this.categoria = Objects.requireNonNull(categoria);
        this.tempRequeridaC = tempRequeridaC;
        this.stockKg = stockKg;
        this.stockMinKg = stockMinKg;
        this.precioPorKg = precioPorKg;
        this.cadenaFrioRota = false;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Categoria getCategoria() { return categoria; }
    public double getTempRequeridaC() { return tempRequeridaC; }
    public double getStockKg() { return stockKg; }
    public double getStockMinKg() { return stockMinKg; }
    public double getPrecioPorKg() { return precioPorKg; }
    public boolean isCadenaFrioRota() { return cadenaFrioRota; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setTempRequeridaC(double tempRequeridaC) { this.tempRequeridaC = tempRequeridaC; }
    public void setStockMinKg(double stockMinKg) { this.stockMinKg = stockMinKg; }
    public void setPrecioPorKg(double precioPorKg) { this.precioPorKg = precioPorKg; }

    public void ingresarMercaderia(double kg) {
        if (kg < 0) throw new IllegalArgumentException("kg negativo");
        this.stockKg += kg;
    }

    public void descontarStock(double kg) throws StockInsuficienteException {
        if (kg <= 0) throw new IllegalArgumentException("kg debe ser > 0");
        if (kg > stockKg) {
            throw new StockInsuficienteException("Stock insuficiente de "+codigo+" (disponible: "+stockKg+" kg, pedido: "+kg+" kg)");
        }
        stockKg -= kg;
    }

    public boolean bajoStockMinimo() {
        return stockKg <= stockMinKg;
    }

    public void marcarRupturaCadenaFrio() {
        this.cadenaFrioRota = true;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s %-18s cat:%-10s stock:%.2fkg (min:%.2f) $/kg:%.2f %s",
                codigo, nombre, categoria, stockKg, stockMinKg, precioPorKg,
                cadenaFrioRota?"[CADENA FRÍO ROTA]":"");
    }
}

class Cliente {
    private final String rut;
    private String razonSocial;
    private String direccionEntrega;
    private double limiteCredito;
    private double deudaActual;

    public Cliente(String rut, String razonSocial, String direccionEntrega, double limiteCredito, double deudaActual) {
        this.rut = Objects.requireNonNull(rut);
        this.razonSocial = Objects.requireNonNull(razonSocial);
        this.direccionEntrega = Objects.requireNonNull(direccionEntrega);
        this.limiteCredito = limiteCredito;
        this.deudaActual = deudaActual;
    }

    public String getRut() { return rut; }
    public String getRazonSocial() { return razonSocial; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public double getLimiteCredito() { return limiteCredito; }
    public double getDeudaActual() { return deudaActual; }

    public double creditoDisponible() { return Math.max(0, limiteCredito - deudaActual); }

    public void aumentarDeuda(double monto) throws LimiteCreditoExcedidoException {
        if (monto < 0) throw new IllegalArgumentException("monto negativo");
        if (deudaActual + monto > limiteCredito + 1e-9) {
            throw new LimiteCreditoExcedidoException(String.format(Locale.US,
                    "Crédito insuficiente para %s (disp: $%.2f, compra: $%.2f)",
                    razonSocial, creditoDisponible(), monto));
        }
        deudaActual += monto;
    }

    public void pagar(double monto) {
        if (monto < 0) throw new IllegalArgumentException("monto negativo");
        deudaActual = Math.max(0, deudaActual - monto);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s - %s (disp: $%.2f / límite: $%.2f)",
                rut, razonSocial, creditoDisponible(), limiteCredito);
    }
}

class ItemPedido {
    final String codigoProducto;
    final double kg;
    final double precioUnitario; // $/kg al momento del pedido

    ItemPedido(String codigoProducto, double kg, double precioUnitario) {
        this.codigoProducto = codigoProducto;
        this.kg = kg;
        this.precioUnitario = precioUnitario;
    }

    double subtotal() { return kg * precioUnitario; }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s: %.2f kg x $%.2f/kg = $%.2f",
                codigoProducto, kg, precioUnitario, subtotal());
    }
}

enum EstadoPedido { PENDIENTE, DESPACHADO }

class Pedido {
    private static long SEQ = 1;
    final long id;
    final Cliente cliente;
    final LocalDateTime fecha;
    final List<ItemPedido> items = new ArrayList<>();
    EstadoPedido estado = EstadoPedido.PENDIENTE;

    Pedido(Cliente cliente) {
        this.id = SEQ++;
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
    }

    void agregarItem(ItemPedido it) { items.add(it); }
    double total() { return items.stream().mapToDouble(ItemPedido::subtotal).sum(); }
    EstadoPedido getEstado() { return estado; }
    void marcarDespachado() { estado = EstadoPedido.DESPACHADO; }

    @Override
    public String toString() {
        return String.format(Locale.US, "Pedido #%d - %s - %s - Total: $%.2f - %s",
                id, cliente.getRazonSocial(), fecha, total(), estado);
    }
}

// ----- Excepciones personalizadas -----
class StockInsuficienteException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StockInsuficienteException(String msg) { super(msg); }
}
class CadenaFrioRotaException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CadenaFrioRotaException(String msg) { super(msg); }
}
class LimiteCreditoExcedidoException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LimiteCreditoExcedidoException(String msg) { super(msg); }
}
class ProductoNoCongeladoException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductoNoCongeladoException(String msg) { super(msg); }
}
class StockMinimoAlcanzadoException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StockMinimoAlcanzadoException(String msg) { super(msg); }
}

// ======================== SERVICIO PRINCIPAL ========================

class FrioExpressService implements Iterable<Producto> {
    // Productos ordenados por código
    private final TreeMap<String, Producto> productos = new TreeMap<>();
    // Cola de pedidos pendientes de despacho
    private final LinkedList<Pedido> colaPendientes = new LinkedList<>();
    // Índice opcional por categoría
    private final Map<Categoria, Set<String>> indicePorCategoria = new HashMap<>();
    // Clientes
    private final Map<String, Cliente> clientes = new HashMap<>();

    // ---------- Gestión de catálogo ----------
    public void registrarProducto(Producto p) {
        productos.put(p.getCodigo(), p);
        indicePorCategoria.computeIfAbsent(p.getCategoria(), k -> new TreeSet<>()).add(p.getCodigo());
    }

    public Producto obtenerProducto(String codigo) throws ProductoNoCongeladoException {
        Producto p = productos.get(codigo);
        if (p == null) throw new ProductoNoCongeladoException("No existe producto con código "+codigo);
        return p;
    }

    public void registrarIngresoMercaderia(String codigo, double kg) throws ProductoNoCongeladoException {
        Producto p = obtenerProducto(codigo);
        p.ingresarMercaderia(kg);
    }

    public List<Producto> consultarBajoStockMinimo() {
        List<Producto> res = new ArrayList<>();
        for (Producto p : productos.values()) {
            if (p.bajoStockMinimo()) res.add(p);
        }
        return res;
    }

    // ---------- Gestión de clientes ----------
    public void registrarCliente(Cliente c) { clientes.put(c.getRut(), c); }
    public Cliente obtenerCliente(String rut) { return clientes.get(rut); }

    // ---------- Gestión cadena de frío ----------
    public void registrarRupturaCadenaFrio(String codigo, int minutosFuera) throws ProductoNoCongeladoException, CadenaFrioRotaException {
        Producto p = obtenerProducto(codigo);
        if (minutosFuera > 30) {
            p.marcarRupturaCadenaFrio();
            throw new CadenaFrioRotaException("Producto "+codigo+" estuvo "+minutosFuera+" minutos fuera de temperatura óptima");
        }
    }

    // ---------- Pedidos ----------
    /**
     * Procesa un pedido verificando stock y crédito. Si todo está OK, descuenta stock,
     * aumenta deuda y agrega el pedido a la cola de pendientes.
     * @param rutCliente RUT del cliente
     * @param itemsSolicitados mapa codigo->kg
     */
    public Pedido procesarPedido(String rutCliente, Map<String, Double> itemsSolicitados)
            throws ProductoNoCongeladoException, StockInsuficienteException, LimiteCreditoExcedidoException, StockMinimoAlcanzadoException {

        Cliente cli = clientes.get(rutCliente);
        if (cli == null) throw new IllegalArgumentException("Cliente inexistente: "+rutCliente);

        // Validaciones previas: existencia de productos y stock suficiente
        double totalEstimado = 0.0;
        Map<Producto, Double> reserva = new LinkedHashMap<>();
        for (Map.Entry<String, Double> e : itemsSolicitados.entrySet()) {
            String codigo = e.getKey();
            double kg = e.getValue();
            Producto p = obtenerProducto(codigo); // puede lanzar ProductoNoCongeladoException
            if (p.isCadenaFrioRota()) {
                // No impedir la venta automáticamente, pero lo marcamos (negocio podría bloquear)
            }
            if (kg <= 0) throw new IllegalArgumentException("Cantidad inválida para "+codigo);
            if (kg > p.getStockKg()) {
                throw new StockInsuficienteException("Stock insuficiente de "+codigo+" (disponible: "+p.getStockKg()+" kg, pedido: "+kg+" kg)");
            }
            // Verificar que no deje por debajo del stock mínimo
            double saldo = p.getStockKg() - kg;
            if (saldo < 0) throw new StockInsuficienteException("Stock negativo para "+codigo);
            if (saldo <= p.getStockMinKg()) {
                // Cumplir con restricción: cuando alcanza stock mínimo -> excepción
                throw new StockMinimoAlcanzadoException("Alcanza stock mínimo para "+codigo+" al vender "+kg+" kg (saldo quedaría "+saldo+" kg, mínimo "+p.getStockMinKg()+" kg)");
            }
            reserva.put(p, kg);
            totalEstimado += kg * p.getPrecioPorKg();
        }

        // Validar crédito
        cli.aumentarDeuda(totalEstimado); // puede lanzar LimiteCreditoExcedidoException

        // Descontar stock y crear pedido
        Pedido pedido = new Pedido(cli);
        for (Map.Entry<Producto, Double> r : reserva.entrySet()) {
            Producto p = r.getKey();
            double kg = r.getValue();
            p.descontarStock(kg); // puede lanzar StockInsuficienteException
            pedido.agregarItem(new ItemPedido(p.getCodigo(), kg, p.getPrecioPorKg()));
        }

        // Encolar para despacho
        colaPendientes.addLast(pedido);
        return pedido;
    }

    public List<Pedido> listarPedidosPendientes() {
        return new ArrayList<>(colaPendientes);
    }

    public Pedido despacharSiguiente() {
        if (colaPendientes.isEmpty()) return null;
        Pedido p = colaPendientes.removeFirst();
        p.marcarDespachado();
        return p;
    }

    public List<Producto> buscarPorCategoria(Categoria cat) {
        Set<String> cods = indicePorCategoria.getOrDefault(cat, Collections.emptySet());
        List<Producto> res = new ArrayList<>();
        for (String c : cods) res.add(productos.get(c));
        res.sort(Comparator.comparing(Producto::getCodigo));
        return res;
    }

    @Override
    public Iterator<Producto> iterator() {
        // Permite iterar productos para reportes
        return productos.values().iterator();
    }
}

// ======================== APP DE DEMOSTRACIÓN (CASOS DE PRUEBA) ========================

public class FrioExpressApp {
    public static void main(String[] args) {
        FrioExpressService svc = new FrioExpressService();

        // --- Datos iniciales ---
        // 8 productos
        svc.registrarProducto(new Producto("C001", "Carne vacuna molida", Categoria.CARNES, -18, 500, 80, 350));
        svc.registrarProducto(new Producto("C002", "Pechuga de pollo", Categoria.CARNES, -18, 420, 70, 280));
        svc.registrarProducto(new Producto("P101", "Merluza filet", Categoria.PESCADOS, -18, 300, 60, 420));
        svc.registrarProducto(new Producto("P102", "Langostino", Categoria.PESCADOS, -18, 120, 40, 980));
        svc.registrarProducto(new Producto("V201", "Brócoli", Categoria.VEGETALES, -18, 90, 50, 190)); // cerca del mínimo
        svc.registrarProducto(new Producto("V202", "Arvejas", Categoria.VEGETALES, -18, 65, 50, 160)); // cerca del mínimo
        svc.registrarProducto(new Producto("H301", "Helado vainilla", Categoria.HELADOS, -18, 200, 50, 450));
        svc.registrarProducto(new Producto("H302", "Helado chocolate", Categoria.HELADOS, -18, 210, 50, 470));

        // 4 clientes
        svc.registrarCliente(new Cliente("21.000.001-0", "Super El Trigal", "Av. Principal 123", 250000, 0));
        svc.registrarCliente(new Cliente("21.000.002-8", "Resto La Bahía", "Rbla. del Puerto 456", 120000, 0));
        svc.registrarCliente(new Cliente("21.000.003-6", "MiniMarket 24hs", "Plaza 789", 60000, 20000));
        svc.registrarCliente(new Cliente("21.000.004-4", "Kiosco Sol", "Diagonal 12", 30000, 0));

        // Mostrar catálogo inicial
        System.out.println("=== Catálogo inicial ===");
        for (Producto p : svc) System.out.println(p);
        System.out.println();

        // ------------------ Casos de prueba obligatorios ------------------

        // 1) Procesamiento exitoso de un pedido dentro del límite de crédito
        try {
            Map<String, Double> pedidoOK = new LinkedHashMap<>();
            pedidoOK.put("C001", 50.0); // 50 kg
            pedidoOK.put("P101", 40.0); // 40 kg
            Pedido ped1 = svc.procesarPedido("21.000.001-0", pedidoOK);
            System.out.println("[OK] Pedido procesado: " + ped1);
        } catch (Exception e) {
            System.out.println("[ERROR inesperado] " + e.getMessage());
        }

        // 2) Intento de pedido que excede el stock disponible
        try {
            Map<String, Double> pedidoStock = new LinkedHashMap<>();
            pedidoStock.put("P102", 1000.0); // excede stock (solo 120 kg)
            svc.procesarPedido("21.000.002-8", pedidoStock);
            System.out.println("[ERROR] Debería haber fallado por stock insuficiente");
        } catch (StockInsuficienteException e) {
            System.out.println("[OK] Stock insuficiente detectado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR distinto] " + e.getMessage());
        }

        // 3) Cliente que intenta exceder su límite de crédito
        try {
            Map<String, Double> pedidoCredito = new LinkedHashMap<>();
            pedidoCredito.put("H301", 500.0); // costoso para crédito de Kiosco Sol (30.000)
            svc.procesarPedido("21.000.004-4", pedidoCredito);
            System.out.println("[ERROR] Debería haber fallado por crédito excedido");
        } catch (LimiteCreditoExcedidoException e) {
            System.out.println("[OK] Crédito excedido detectado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR distinto] " + e.getMessage());
        }

        // 4) Alerta por producto que rompe cadena de frío (>30 min)
        try {
            svc.registrarRupturaCadenaFrio("V201", 45);
            System.out.println("[ERROR] Debería haber alertado por ruptura cadena de frío");
        } catch (CadenaFrioRotaException e) {
            System.out.println("[OK] Alerta cadena de frío: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR distinto] " + e.getMessage());
        }

        // 5) Generación de reporte de productos bajo stock mínimo
        System.out.println("\n=== Reporte: Productos bajo o cerca del stock mínimo ===");
        for (Producto p : svc.consultarBajoStockMinimo()) {
            System.out.println(p);
        }

        // 6) Intento de procesar un pedido con producto de código inexistente
        try {
            Map<String, Double> pedidoCodigo = new LinkedHashMap<>();
            pedidoCodigo.put("ZZZ999", 5.0);
            svc.procesarPedido("21.000.002-8", pedidoCodigo);
            System.out.println("[ERROR] Debería haber fallado por código inexistente");
        } catch (ProductoNoCongeladoException e) {
            System.out.println("[OK] Código inexistente detectado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR distinto] " + e.getMessage());
        }

        // Mostrar cola de pendientes
        System.out.println("\n=== Pedidos pendientes de despacho ===");
        for (Pedido p : svc.listarPedidosPendientes()) System.out.println(p);

        // Despachar el siguiente
        Pedido despachado = svc.despacharSiguiente();
        System.out.println("\nDespachado: " + (despachado != null ? despachado : "(ninguno)"));

        // Estado final del catálogo (iterable)
        System.out.println("\n=== Catálogo final ===");
        for (Producto p : svc) System.out.println(p);
    }
}
