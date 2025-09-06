package com.frioexpress;

import java.util.*;
import java.util.stream.Collectors;

public class Inventario {
    private final TreeMap<String, Producto> productos = new TreeMap<>();
    private final Map<Categoria, List<String>> indicePorCategoria = new HashMap<>();

    public void altaProducto(Producto p) {
        productos.put(p.getCodigo(), p);
        indicePorCategoria.computeIfAbsent(p.getCategoria(), k -> new ArrayList<>()).add(p.getCodigo());
    }

    public Producto get(String codigo) throws ProductoNoCongeladoException {
        Producto p = productos.get(codigo);
        if (p == null) throw new ProductoNoCongeladoException("No existe producto con código: "+codigo);
        return p;
    }

    public Collection<Producto> todosOrdenados() {
        return productos.values();
    }

    public List<Producto> porCategoria(Categoria c) {
        List<String> cods = indicePorCategoria.getOrDefault(c, Collections.emptyList());
        return cods.stream().map(productos::get).collect(Collectors.toList());
    }

    public List<Producto> bajoMinimo() {
        return productos.values().stream()
                .filter(p -> p.getStockKg() <= p.getStockMinKg())
                .collect(Collectors.toList());
    }
}
