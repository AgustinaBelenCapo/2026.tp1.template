package com.bibliotech.model;

public record SocioEstudiante(
        int id,
        String dni,
        String nombre,
        String email
) implements Socio {

    @Override
    public TipoSocio tipo() {
        return TipoSocio.ESTUDIANTE;
    }

    @Override
    public int limitePrestamos() {
        return 3;
    }
}
