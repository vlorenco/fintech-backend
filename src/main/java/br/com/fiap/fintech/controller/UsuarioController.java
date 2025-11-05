package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.model.Usuario;
import br.com.fiap.fintech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String email = loginData.get("email");
            String senha = loginData.get("senha");

            Usuario usuario = authService.autenticar(email, senha);

            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Login realizado com sucesso!");
            response.put("usuario", usuario);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.status(401).body(erro);
        }
    }
}
