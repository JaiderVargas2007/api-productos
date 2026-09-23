package com.inventario.api_productos.exception;

// Reto 2: se lanza cuando se quiere sacar mas de lo que hay
public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
