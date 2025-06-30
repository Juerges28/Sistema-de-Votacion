package com.universidad.sistemavotos.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.universidad.sistemavotos.model.GoogleLoginRequest;
import com.universidad.sistemavotos.model.Votante;
import com.universidad.sistemavotos.repository.VotanteRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final VotanteRepository votanteRepository;

    public AuthController(VotanteRepository votanteRepository) {
        this.votanteRepository = votanteRepository;
    }

    @PostMapping("/google")
    public Object loginWithGoogle(@RequestBody GoogleLoginRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList("227573172566-1ndu5ohh2i6n7purm6hkikv2nk4fimcv.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                //String nombre = (String) payload.get("name");

                // Buscar si ya está registrado
                Optional<Votante> votante = votanteRepository.findByCorreoElectronico(email);
                if (votante != null) {
                    return votante; // Ya registrado
                } else {
                    // Redirigir a completar registro
                    return Collections.singletonMap("message", "Registro incompleto");
                }
            } else {
                return Collections.singletonMap("error", "Token inválido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "Fallo al verificar token");
        }
    }
}
