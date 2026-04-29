package com.bibliotech.model;

public record LibroFisico(
        String isbn,
        String titulo,
        String autor,
        int añoPublicacion,
        CategoriaRecurso categoria,
        String ubicacionEstante
) implements Recurso {
}
