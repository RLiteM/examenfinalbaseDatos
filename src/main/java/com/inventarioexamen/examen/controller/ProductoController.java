package com.inventarioexamen.examen.controller;

import com.inventarioexamen.examen.dto.MovimientoInventarioDTO;
import com.inventarioexamen.examen.dto.ProductoDTO;
import com.inventarioexamen.examen.service.InventarioService;
import com.inventarioexamen.examen.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<ProductoDTO> getAllProductos(@RequestParam(name = "enStock", required = false) Boolean enStock) {
        return productoService.getAllProductos(enStock);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        Optional<ProductoDTO> producto = productoService.getProductoById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/movimientos")
    public List<MovimientoInventarioDTO> getHistorialMovimientos(@PathVariable Long id) {
        return inventarioService.getMovimientosByProductoId(id);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductoDTO> getProductoBySku(@PathVariable String sku) {
        Optional<ProductoDTO> producto = productoService.getProductoBySku(sku);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductoDTO createProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.saveProducto(productoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDetails) {
        return productoService.getProductoById(id)
                .map(existingProduct -> {
                    productoDetails.setProductoId(id);
                    ProductoDTO updatedProducto = productoService.saveProducto(productoDetails);
                    return ResponseEntity.ok(updatedProducto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
