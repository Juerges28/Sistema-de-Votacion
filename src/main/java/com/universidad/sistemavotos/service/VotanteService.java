package com.universidad.sistemavotos.service;

import com.universidad.sistemavotos.model.Votante;
import com.universidad.sistemavotos.repository.VotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotanteService {

    @Autowired
    private VotanteRepository votanteRepository;

    public Votante registrarVotante(Votante votante) {
        return votanteRepository.save(votante);
    }

    public Optional<Votante> buscarPorCorreo(String correo) {
        return votanteRepository.findByCorreoElectronico(correo);
    }
}
