package com.inventarioexamen.examen.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoDTO {
    private Long productoId;
    private String nombre;
    private String sku;
    private BigDecimal stockActual;
    private Long proveedorId;
    private String nombreProveedor; // Para conveniencia en la respuesta
}
