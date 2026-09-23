package com.inventario.api_productos.service;

import com.inventario.api_productos.exception.ProductoNoEncontradoException;
import com.inventario.api_productos.exception.StockInsuficienteException;
import com.inventario.api_productos.model.Producto;
import com.inventario.api_productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    // Reto 1
    public List<Producto> obtenerPorPrecioMenorA(Double precio) {
        return productoRepository.findByPrecioLessThan(precio);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoDetalles) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        producto.setNombre(productoDetalles.getNombre());
        producto.setPrecio(productoDetalles.getPrecio());
        producto.setStock(productoDetalles.getStock());
        producto.setCategoria(productoDetalles.getCategoria());

        return productoRepository.save(producto);
    }

    // Reto 2: reducir el stock, si no alcanza lanza error
    public Producto reducirStock(Long id, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new StockInsuficienteException("La cantidad debe ser mayor a 0");
        }

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente. Disponible: "
                    + producto.getStock() + ", solicitado: " + cantidad);
        }

        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }

    public boolean eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
