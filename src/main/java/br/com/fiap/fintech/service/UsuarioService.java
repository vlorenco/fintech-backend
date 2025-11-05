package br.com.fiap.fintech.service;

import br.com.fiap.fintech.model.Usuario;
import br.com.fiap.fintech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta");
        }

        return usuario;
    }
}
