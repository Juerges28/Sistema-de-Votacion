package com.universidad.sistemavotos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.universidad.sistemavotos.model.Votante;

public interface VotanteRepository extends JpaRepository<Votante, Long> {
    Optional<Votante> findByDocumentoIdentidad(String documentoIdentidad);
    Optional<Votante> findByCorreoElectronico(String correoElectronico);
}
