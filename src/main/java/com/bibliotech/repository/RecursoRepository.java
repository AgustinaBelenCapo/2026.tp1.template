package com.bibliotech.repository;

import com.bibliotech.model.CategoriaRecurso;
import com.bibliotech.model.Recurso;
import java.util.List;

public interface RecursoRepository extends Repository<Recurso, String> {
    List<Recurso> buscarPorTitulo(String titulo);
    List<Recurso> buscarPorAutor(String autor);
    List<Recurso> buscarPorCategoria(CategoriaRecurso categoria);
}
