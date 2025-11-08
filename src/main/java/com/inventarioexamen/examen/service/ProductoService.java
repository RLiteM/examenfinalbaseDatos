package com.inventarioexamen.examen.service;

import com.inventarioexamen.examen.dto.ProductoDTO;
import com.inventarioexamen.examen.entity.Producto;
import com.inventarioexamen.examen.entity.Proveedor;
import com.inventarioexamen.examen.repository.ProductoRepository;
import com.inventarioexamen.examen.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<ProductoDTO> getAllProductos(Boolean enStockSolo) {
        List<Producto> productos;
        if (Boolean.TRUE.equals(enStockSolo)) {
            productos = productoRepository.findByStockActualGreaterThan(java.math.BigDecimal.ZERO);
        } else {
            productos = productoRepository.findAll();
        }
        return productos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductoDTO> getProductoById(Long id) {
        return productoRepository.findById(id).map(this::toDTO);
    }

    public Optional<ProductoDTO> getProductoBySku(String sku) {
        return productoRepository.findBySku(sku).map(this::toDTO);
    }

    public ProductoDTO saveProducto(ProductoDTO productoDTO) {
        Producto producto = toEntity(productoDTO);
        Producto savedProducto = productoRepository.save(producto);
        return toDTO(savedProducto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    private ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setProductoId(producto.getProductoId());
        dto.setNombre(producto.getNombre());
        dto.setSku(producto.getSku());
        dto.setStockActual(producto.getStockActual());
        if (producto.getProveedor() != null) {
            dto.setProveedorId(producto.getProveedor().getProveedorId());
            dto.setNombreProveedor(producto.getProveedor().getNombre());
        }
        return dto;
    }

    private Producto toEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setProductoId(dto.getProductoId());
        producto.setNombre(dto.getNombre());
        producto.setSku(dto.getSku());
        producto.setStockActual(dto.getStockActual());

        if (dto.getProveedorId() != null) {
            Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + dto.getProveedorId()));
            producto.setProveedor(proveedor);
        }
        return producto;
    }
}
