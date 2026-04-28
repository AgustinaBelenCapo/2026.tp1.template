package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.model.Prestamo;
import java.time.LocalDate;
import java.util.List;

public interface PrestamoService {
    Prestamo registrarPrestamo(String isbn, int socioId, LocalDate fechaPrestamo) throws BibliotecaException;
    Prestamo registrarDevolucion(String isbn, LocalDate fechaDevolucion) throws BibliotecaException;
    List<Prestamo> historialPorSocio(int socioId);
}
