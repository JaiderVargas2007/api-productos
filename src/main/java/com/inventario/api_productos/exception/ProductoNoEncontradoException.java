package com.inventario.api_productos.exception;

// se lanza cuando no existe un producto con ese id
public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        super("Producto no encontrado con el ID: " + id);
    }
}
