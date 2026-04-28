package com.bibliotech.model;

public record LibroFisico(
        String isbn,
        String titulo,
        String autor,
        int anioPublicacion,
        CategoriaRecurso categoria,
        String ubicacionEstante
) implements Recurso {
}
