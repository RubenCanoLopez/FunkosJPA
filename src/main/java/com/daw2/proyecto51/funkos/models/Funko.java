package com.daw2.proyecto51.funkos.models;

import com.daw2.proyecto51.categorias.models.Categoria;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "funkos", indexes = {
        @Index(name = "idx_funko_nombre", columnList = "nombre"),
        @Index(name = "idx_funko_deleted", columnList = "is_deleted")
        })

public class Funko {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true, length = 100)
    private String nombre;

    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column (nullable = false)
    private Integer cantidad;

    /** LA RELACIÓN. El lado dueño: aquí está la clave ajena. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false,
            foreignKey = @ForeignKey(name= "fk_funko_categoria"))
    private Categoria categoria;

    @Column (name = "is_deleted", nullable = false)
    private Boolean deleted = false;

    @Column (name = "created_at", updatable = false)
    private LocalDateTime cratedAt = LocalDateTime.now();

    @Column (name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    protected Funko() {}

    public Funko(String nombre, BigDecimal precio, Integer cantidad, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    /** Hibernate lo llama antes de cada update */
    @PreUpdate
    void alActualizar() {
        this.updatedAt = LocalDateTime.now();
    }

}
