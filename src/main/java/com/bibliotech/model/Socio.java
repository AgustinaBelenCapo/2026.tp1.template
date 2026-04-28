package com.bibliotech.model;

public interface Socio {
    int id();
    String dni();
    String nombre();
    String email();
    TipoSocio tipo();
    int limitePrestamos();
}
