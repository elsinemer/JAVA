package com.frioexpress;

public class LimiteCreditoExcedidoException extends Exception {
    private static final long serialVersionUID = 1L;

	public LimiteCreditoExcedidoException(String message) {
        super(message);
    }
}
