package com.frioexpress;

public class ProductoNoCongeladoException extends Exception {
    private static final long serialVersionUID = 1L;

	public ProductoNoCongeladoException(String message) {
        super(message);
    }
}
