package com.daw2.proyecto51.categorias.controllers;

import com.daw2.proyecto51.categorias.dto.CategoriaCreateRequest;
import com.daw2.proyecto51.categorias.dto.CategoriaResponse;
import com.daw2.proyecto51.categorias.services.CategoriasService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("${api.path:/api}/${api.version:v1}/categorias")
public class CategoriasRestController {

    private final CategoriasService servicio;

    public CategoriasRestController(CategoriasService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> findAll (
            @PageableDefault(size = 20, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(servicio.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> save(
            @Valid @RequestBody CategoriaCreateRequest dto) {
        var creada = servicio.save(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/categorias/" + creada.id()))
                .body(creada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servicio.deleteById(id);;

        return ResponseEntity.noContent().build();
    }
}