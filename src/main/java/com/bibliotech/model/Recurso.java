package com.bibliotech.model;

public interface Recurso {
    String isbn();
    String titulo();
    String autor();
    int anioPublicacion();
    CategoriaRecurso categoria();
}
