package com.bibliotech.model;

public record SocioDocente(
        int id,
        String dni,
        String nombre,
        String email
) implements Socio {

    @Override
    public TipoSocio tipo() {
        return TipoSocio.DOCENTE;
    }

    @Override
    public int limitePrestamos() {
        return 5;
    }
}
