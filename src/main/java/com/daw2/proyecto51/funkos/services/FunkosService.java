package com.daw2.proyecto51.funkos.services;

import com.daw2.proyecto51.funkos.dto.*;
import org.springframework.data.domain.*;

public interface FunkosService {
    Page<FunkoResponse> findAll(Pageable pageable);
    FunkoResponse findById(Long id);
    FunkoResponse save(FunkoCreateRequest request);
    FunkoResponse replace(Long id, FunkoCreateRequest request);
    FunkoResponse update(Long id, FunkoUpdateRequest request);
    void deleteById(Long id);
}
