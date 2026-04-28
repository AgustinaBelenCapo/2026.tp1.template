package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.EntidadNoEncontradaException;
import com.bibliotech.exception.LibroNoDisponibleException;
import com.bibliotech.exception.LimitePrestamosExcedidoException;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.repository.RecursoRepository;
import com.bibliotech.repository.SocioRepository;
import java.time.LocalDate;
import java.util.List;

public class PrestamoServiceImpl implements PrestamoService {
    private final RecursoRepository recursoRepository;
    private final SocioRepository socioRepository;
    private final PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(
            RecursoRepository recursoRepository,
            SocioRepository socioRepository,
            PrestamoRepository prestamoRepository
    ) {
        this.recursoRepository = recursoRepository;
        this.socioRepository = socioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public Prestamo registrarPrestamo(String isbn, int socioId, LocalDate fechaPrestamo) throws BibliotecaException {
        if (recursoRepository.buscarPorId(isbn).isEmpty()) {
            throw new EntidadNoEncontradaException("No existe recurso con ISBN: " + isbn);
        }

        Socio socio = socioRepository.buscarPorId(socioId)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe socio con ID: " + socioId));

        if (prestamoRepository.buscarPrestamoActivoPorIsbn(isbn).isPresent()) {
            throw new LibroNoDisponibleException("El recurso ya se encuentra prestado: " + isbn);
        }

        int prestamosActivos = prestamoRepository.buscarPrestamosActivosPorSocio(socioId).size();
        if (prestamosActivos >= socio.limitePrestamos()) {
            throw new LimitePrestamosExcedidoException(
                    "El socio supero su limite de prestamos activos: " + socio.limitePrestamos());
        }

        Prestamo prestamo = new Prestamo(isbn, socioId, fechaPrestamo, fechaPrestamo.plusDays(14), java.util.Optional.empty());
        prestamoRepository.guardar(prestamo);
        return prestamo;
    }

    @Override
    public Prestamo registrarDevolucion(String isbn, LocalDate fechaDevolucion) throws BibliotecaException {
        Prestamo activo = prestamoRepository.buscarPrestamoActivoPorIsbn(isbn)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe prestamo activo para ISBN: " + isbn));
        Prestamo actualizado = activo.devolver(fechaDevolucion);
        prestamoRepository.guardar(actualizado);
        return actualizado;
    }

    @Override
    public List<Prestamo> historialPorSocio(int socioId) {
        return prestamoRepository.historialPorSocio(socioId);
    }
}
