package br.com.fiap.fintech;

import br.com.fiap.fintech.model.Usuario;
import br.com.fiap.fintech.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FintechApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintechApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                usuarioRepository.save(new Usuario(
                        "Usuário Teste",
                        "teste@fintech.com",
                        "123456"
                ));
                System.out.println("✅ Usuário de teste criado com sucesso!");
                System.out.println("📧 Email: teste@fintech.com");
                System.out.println("🔑 Senha: 123456");
            } else {
                System.out.println("ℹ️ Usuário de teste já existe no banco.");
            }
        };
    }
}
