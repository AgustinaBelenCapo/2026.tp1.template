package com.bibliotech.repository;

import com.bibliotech.model.Socio;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ImplementacionSocioRepository implements SocioRepository {
    private final Map<Integer, Socio> socios = new ConcurrentHashMap<>();

    @Override
    public void guardar(Socio entidad) {
        socios.put(entidad.id(), entidad);
    }

    @Override
    public Optional<Socio> buscarPorId(Integer id) {
        return Optional.ofNullable(socios.get(id));
    }

    @Override
    public java.util.List<Socio> buscarTodos() {
        return new ArrayList<>(socios.values());
    }

    @Override
    public Optional<Socio> buscarPorDni(String dni) {
        return socios.values().stream()
                .filter(s -> s.dni().equals(dni))
                .findFirst();
    }
}
