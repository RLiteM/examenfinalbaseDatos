package com.inventarioexamen.examen.service;

import com.inventarioexamen.examen.dto.ProveedorDTO;
import com.inventarioexamen.examen.entity.Proveedor;
import com.inventarioexamen.examen.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<ProveedorDTO> getAllProveedores() {
        return proveedorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProveedorDTO> getProveedorById(Long id) {
        return proveedorRepository.findById(id)
                .map(this::toDTO);
    }

    public ProveedorDTO saveProveedor(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = toEntity(proveedorDTO);
        Proveedor savedProveedor = proveedorRepository.save(proveedor);
        return toDTO(savedProveedor);
    }

    public void deleteProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    private ProveedorDTO toDTO(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setProveedorId(proveedor.getProveedorId());
        dto.setNombre(proveedor.getNombre());
        dto.setPaisOrigen(proveedor.getPaisOrigen());
        return dto;
    }

    private Proveedor toEntity(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setProveedorId(dto.getProveedorId());
        proveedor.setNombre(dto.getNombre());
        proveedor.setPaisOrigen(dto.getPaisOrigen());
        return proveedor;
    }
}
