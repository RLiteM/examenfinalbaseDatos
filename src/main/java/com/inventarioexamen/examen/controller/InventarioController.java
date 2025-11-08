package com.inventarioexamen.examen.controller;

import com.inventarioexamen.examen.dto.MovimientoInventarioDTO;
import com.inventarioexamen.examen.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/movimientos")
    public List<MovimientoInventarioDTO> getAllMovimientos() {
        return inventarioService.getAllMovimientos();
    }

    @PostMapping("/movimientos")
    public ResponseEntity<?> registrarMovimiento(@RequestBody MovimientoInventarioDTO movimientoDTO) {
        try {
            MovimientoInventarioDTO nuevoMovimiento = inventarioService.registrarMovimiento(movimientoDTO);
            return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
