package com.inventarioexamen.examen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "movimiento_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long movimientoId;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "tipo", nullable = false, length = 1)
    private char tipo;

    @Column(name = "cantidad", nullable = false, precision = 14, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "fecha", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fecha;

    @Column(name = "referencia", length = 80)
    private String referencia;
}
