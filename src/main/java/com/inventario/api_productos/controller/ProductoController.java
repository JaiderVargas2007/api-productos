package com.inventario.api_productos.controller;

import com.inventario.api_productos.exception.ProductoNoEncontradoException;
import com.inventario.api_productos.exception.StockInsuficienteException;
import com.inventario.api_productos.model.Producto;
import com.inventario.api_productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET: Obtener todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.obtenerTodos();
    }

    // GET: Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(producto -> ResponseEntity.ok(producto))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Filtrar por categoria
    @GetMapping("/categoria/{categoria}")
    public List<Producto> listarPorCategoria(@PathVariable String categoria) {
        return productoService.obtenerPorCategoria(categoria);
    }

    // Reto 1 - GET: productos con precio menor a ?precio=X
    // ej: /api/productos/precio-menor?precio=100
    @GetMapping("/precio-menor")
    public List<Producto> listarPorPrecioMenor(@RequestParam Double precio) {
        return productoService.obtenerPorPrecioMenorA(precio);
    }

    // POST: Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardar(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // PUT: Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto actualizado = productoService.actualizar(id, producto);
            return ResponseEntity.ok(actualizado);
        } catch (ProductoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Reto 2 - PATCH: reducir stock
    // ej: /api/productos/1/reducir-stock?cantidad=3
    // 200 si se pudo, 400 si no hay stock suficiente, 404 si no existe
    @PatchMapping("/{id}/reducir-stock")
    public ResponseEntity<?> reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            Producto producto = productoService.reducirStock(id, cantidad);
            return ResponseEntity.ok(producto);
        } catch (StockInsuficienteException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (ProductoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE: Eliminar producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
