package com.daw2.proyecto51.funkos.controllers;

import com.daw2.proyecto51.funkos.dto.FunkoCreateRequest;
import com.daw2.proyecto51.funkos.dto.FunkoResponse;
import com.daw2.proyecto51.funkos.dto.FunkoUpdateRequest;
import com.daw2.proyecto51.funkos.services.FunkosService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("${api.path:/api}/${api.version:v1}/funkos")
public class FunkosRestController {

    private final FunkosService servicio;

    public FunkosRestController(FunkosService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<Page<FunkoResponse>> buscar(
            @RequestParam Optional<String> categoria,
            @RequestParam Optional<String> nombre,
            @RequestParam Optional<BigDecimal> precioMax,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {

        return ResponseEntity.ok(
                servicio.buscar(categoria, nombre, precioMax, sanear(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunkoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.findById(id));
    }

    @PostMapping
    public ResponseEntity<FunkoResponse> save(@Valid @RequestBody FunkoCreateRequest dto) {
        var creado = servicio.save(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/funkos/" + creado.id()))
                .body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FunkoResponse> replace(
            @PathVariable Long id, @Valid @RequestBody FunkoCreateRequest dto) {

        return ResponseEntity.ok(servicio.replace(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FunkoResponse> update(
            @PathVariable Long id, @Valid @RequestBody FunkoUpdateRequest dto) {

        return ResponseEntity.ok(servicio.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servicio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /** Lista blanca de campos ordenables y tope de tamaño. */
    private Pageable sanear(Pageable p) {
        var permitidos = Set.of("id", "nombre", "precio", "cantidad", "createdAt");

        var orden = p.getSort().stream()
                .filter(o -> permitidos.contains(o.getProperty()))
                .toList();

        return PageRequest.of(
                p.getPageNumber(),
                Math.min(p.getPageSize(), 100),
                orden.isEmpty() ? Sort.by("id") : Sort.by(orden).and(Sort.by("id")));
    }
}