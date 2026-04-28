package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.model.Socio;
import java.util.Optional;

public interface SocioService {
    void registrarSocio(Socio socio) throws BibliotecaException;
    Optional<Socio> buscarPorId(int id);
}
