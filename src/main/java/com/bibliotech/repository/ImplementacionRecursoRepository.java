package com.bibliotech.repository;

import com.bibliotech.model.CategoriaRecurso;
import com.bibliotech.model.Recurso;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ImplementacionRecursoRepository implements RecursoRepository {
    private final Map<String, Recurso> recursos = new ConcurrentHashMap<>();

    @Override
    public void guardar(Recurso entidad) {
        recursos.put(entidad.isbn(), entidad);
    }

    @Override
    public Optional<Recurso> buscarPorId(String id) {
        return Optional.ofNullable(recursos.get(id));
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(recursos.values());
    }

    @Override
    public List<Recurso> buscarPorTitulo(String titulo) {
        return recursos.values().stream()
                .filter(r -> r.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorAutor(String autor) {
        return recursos.values().stream()
                .filter(r -> r.autor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorCategoria(CategoriaRecurso categoria) {
        return recursos.values().stream()
                .filter(r -> r.categoria() == categoria)
                .toList();
    }
}
