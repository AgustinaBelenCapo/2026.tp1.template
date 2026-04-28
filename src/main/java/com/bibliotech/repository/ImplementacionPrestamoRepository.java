package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ImplementacionPrestamoRepository implements PrestamoRepository {
    private final List<Prestamo> prestamos = new CopyOnWriteArrayList<>();

    @Override
    public void guardar(Prestamo entidad) {
        prestamos.removeIf(p ->
                p.isbn().equals(entidad.isbn()) &&
                p.socioId() == entidad.socioId() &&
                p.fechaPrestamo().equals(entidad.fechaPrestamo()));
        prestamos.add(entidad);
    }

    @Override
    public Optional<Prestamo> buscarPorId(String id) {
        return prestamos.stream()
                .filter(p -> (p.isbn() + "-" + p.socioId() + "-" + p.fechaPrestamo()).equals(id))
                .findFirst();
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public Optional<Prestamo> buscarPrestamoActivoPorIsbn(String isbn) {
        return prestamos.stream()
                .filter(p -> p.isbn().equals(isbn) && p.estaActivo())
                .findFirst();
    }

    @Override
    public List<Prestamo> buscarPrestamosActivosPorSocio(int socioId) {
        return prestamos.stream()
                .filter(p -> p.socioId() == socioId && p.estaActivo())
                .toList();
    }

    @Override
    public List<Prestamo> historialPorSocio(int socioId) {
        return prestamos.stream()
                .filter(p -> p.socioId() == socioId)
                .toList();
    }
}
