package com.inventarioexamen.examen.controller;

import com.inventarioexamen.examen.entity.MovimientoInventario;
import com.inventarioexamen.examen.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @PostMapping("/movimientos")
    public ResponseEntity<?> registrarMovimiento(@RequestBody MovimientoInventario movimiento) {
        try {
            MovimientoInventario nuevoMovimiento = inventarioService.registrarMovimiento(movimiento);
            return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
