package com.daw2.proyecto51.funkos.services;

import com.daw2.proyecto51.funkos.dto.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Optional;

public interface FunkosService {
    Page<FunkoResponse> findAll(Pageable pageable);
    Page<FunkoResponse> buscar(Optional<String> categoria,
                               Optional<String> nombre,
                               Optional<BigDecimal> precioMax,
                               Pageable pageable);
    FunkoResponse findById(Long id);
    FunkoResponse save(FunkoCreateRequest request);
    FunkoResponse replace(Long id, FunkoCreateRequest request);
    FunkoResponse update(Long id, FunkoUpdateRequest request);
    void deleteById(Long id);
}
