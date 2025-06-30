package com.universidad.sistemavotos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.sistemavotos.model.Votante;
import com.universidad.sistemavotos.service.VotanteService;

@RestController
@RequestMapping("/api/votantes")
public class VotanteController {

    @Autowired
    private VotanteService votanteService;

    @PostMapping("/registrar")
    public Votante registrar(@RequestBody Votante votante) {
        return votanteService.registrarVotante(votante);
    }
}
