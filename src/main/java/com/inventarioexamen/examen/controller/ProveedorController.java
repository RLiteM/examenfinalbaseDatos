package com.inventarioexamen.examen.controller;

import com.inventarioexamen.examen.entity.Proveedor;
import com.inventarioexamen.examen.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> getAllProveedores() {
        return proveedorService.getAllProveedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.getProveedorById(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Proveedor createProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.saveProveedor(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorDetails) {
        Optional<Proveedor> proveedorOptional = proveedorService.getProveedorById(id);
        if (proveedorOptional.isPresent()) {
            Proveedor proveedor = proveedorOptional.get();
            proveedor.setNombre(proveedorDetails.getNombre());
            proveedor.setPaisOrigen(proveedorDetails.getPaisOrigen());
            return ResponseEntity.ok(proveedorService.saveProveedor(proveedor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.deleteProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
