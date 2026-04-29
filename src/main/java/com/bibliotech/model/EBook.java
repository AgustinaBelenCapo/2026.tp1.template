package com.bibliotech.model;

public record EBook(
        String isbn,
        String titulo,
        String autor,
        int añoPublicacion,
        CategoriaRecurso categoria,
        String formatoArchivo
) implements Recurso {
}
