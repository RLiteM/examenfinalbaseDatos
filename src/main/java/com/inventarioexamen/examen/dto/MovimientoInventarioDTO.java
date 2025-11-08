package com.inventarioexamen.examen.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class MovimientoInventarioDTO {
    private Long movimientoId;
    private Long productoId;
    private String nombreProducto; // Para conveniencia en la respuesta
    private char tipo;
    private BigDecimal cantidad;
    private Timestamp fecha;
    private String referencia;
}
