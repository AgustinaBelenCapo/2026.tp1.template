package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.DatoInvalidoException;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.SocioRepository;
import java.util.Optional;
import java.util.regex.Pattern;

public class SocioServiceImpl implements SocioService {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private final SocioRepository socioRepository;

    public SocioServiceImpl(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Override
    public void registrarSocio(Socio socio) throws BibliotecaException {
        if (socioRepository.buscarPorDni(socio.dni()).isPresent()) {
            throw new DatoInvalidoException("Ya existe un socio con DNI: " + socio.dni());
        }
        if (!EMAIL_PATTERN.matcher(socio.email()).matches()) {
            throw new DatoInvalidoException("Email invalido: " + socio.email());
        }
        socioRepository.guardar(socio);
    }

    @Override
    public Optional<Socio> buscarPorId(int id) {
        return socioRepository.buscarPorId(id);
    }
}
