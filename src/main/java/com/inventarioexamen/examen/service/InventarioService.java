package com.inventarioexamen.examen.service;

import com.inventarioexamen.examen.dto.MovimientoInventarioDTO;
import com.inventarioexamen.examen.entity.MovimientoInventario;
import com.inventarioexamen.examen.entity.Producto;
import com.inventarioexamen.examen.repository.MovimientoInventarioRepository;
import com.inventarioexamen.examen.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public MovimientoInventarioDTO registrarMovimiento(MovimientoInventarioDTO movimientoDTO) {
        Producto producto = productoRepository.findById(movimientoDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + movimientoDTO.getProductoId()));

        BigDecimal cantidad = movimientoDTO.getCantidad();
        char tipo = movimientoDTO.getTipo();

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

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setTipo(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setReferencia(movimientoDTO.getReferencia());

        MovimientoInventario savedMovimiento = movimientoInventarioRepository.save(movimiento);
        return toDTO(savedMovimiento);
    }

    public List<MovimientoInventarioDTO> getMovimientosByProductoId(Long productoId) {
        return movimientoInventarioRepository.findByProductoProductoId(productoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MovimientoInventarioDTO toDTO(MovimientoInventario movimiento) {
        MovimientoInventarioDTO dto = new MovimientoInventarioDTO();
        dto.setMovimientoId(movimiento.getMovimientoId());
        dto.setProductoId(movimiento.getProducto().getProductoId());
        dto.setNombreProducto(movimiento.getProducto().getNombre());
        dto.setTipo(movimiento.getTipo());
        dto.setCantidad(movimiento.getCantidad());
        dto.setFecha(movimiento.getFecha());
        dto.setReferencia(movimiento.getReferencia());
        return dto;
    }
}
