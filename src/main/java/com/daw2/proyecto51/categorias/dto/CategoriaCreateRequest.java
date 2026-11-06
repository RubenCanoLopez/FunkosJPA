package com.daw2.proyecto51.categorias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaCreateRequest(
        @NotBlank @Size(max = 50) String nombre
) {}
