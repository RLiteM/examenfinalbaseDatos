package com.inventarioexamen.examen.service;

import com.inventarioexamen.examen.entity.MovimientoInventario;
import com.inventarioexamen.examen.entity.Producto;
import com.inventarioexamen.examen.repository.MovimientoInventarioRepository;
import com.inventarioexamen.examen.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class InventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public MovimientoInventario registrarMovimiento(MovimientoInventario movimiento) {
        Producto producto = productoRepository.findById(movimiento.getProducto().getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + movimiento.getProducto().getProductoId()));

        BigDecimal cantidad = movimiento.getCantidad();
        char tipo = movimiento.getTipo();

        if (tipo == 'E') {
            producto.setStockActual(producto.getStockActual().add(cantidad));
        } else if (tipo == 'S') {
            if (producto.getStockActual().compareTo(cantidad) < 0) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            producto.setStockActual(producto.getStockActual().subtract(cantidad));
        } else {
            throw new RuntimeException("Tipo de movimiento no válido: " + tipo);
        }

        productoRepository.save(producto);
        return movimientoInventarioRepository.save(movimiento);
    }
}
