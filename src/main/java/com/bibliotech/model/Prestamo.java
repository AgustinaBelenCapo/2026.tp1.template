package com.bibliotech.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public record Prestamo(
        String isbn,
        int socioId,
        LocalDate fechaPrestamo,
        LocalDate fechaVencimiento,
        Optional<LocalDate> fechaDevolucion
) {
    public Prestamo devolver(LocalDate fecha) {
        return new Prestamo(isbn, socioId, fechaPrestamo, fechaVencimiento, Optional.of(fecha));
    }

    public boolean estaActivo() {
        return fechaDevolucion.isEmpty();
    }

    public long diasRetraso() {
        if (fechaDevolucion.isEmpty()) {
            return 0;
        }
        long dias = ChronoUnit.DAYS.between(fechaVencimiento, fechaDevolucion.get());
        return Math.max(dias, 0);
    }
}
