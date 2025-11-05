package br.com.fiap.fintech.service;

import br.com.fiap.fintech.model.Receita;
import br.com.fiap.fintech.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service // 1. Indica que esta é uma classe de Serviço (o "cérebro")
public class ReceitaService {

    @Autowired // 2. Pede ao Spring para "injetar" (fornecer) o Repository aqui
    private ReceitaRepository receitaRepository;

    // --- Método para CRIAR (POST) ---
    public Receita criarReceita(Receita receita) {
        // --- REGRA DE NEGÓCIO ---
        // Exemplo: Não permitir salvar uma receita com valor zero ou negativo.
        if (receita.getValor() == null || receita.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            // No mundo real, você lançaria uma exceção customizada
            throw new IllegalArgumentException("O valor da receita deve ser positivo.");
        }
        // --- FIM DA REGRA ---

        // Se a regra passar, salva no banco
        return receitaRepository.save(receita);
    }

    // --- Método para LER TODOS (GET) ---
    public List<Receita> listarTodasReceitas() {
        return receitaRepository.findAll();
    }

    // --- Método para LER TODOS COM FILTROS ---
    public List<Receita> listarReceitasComFiltros(Integer categoria, LocalDate dataInicio, LocalDate dataFim, 
                                                   String status, String ordenacao, String ordem) {
        
        // Converte status string para enum
        Receita.StatusTransacao statusEnum = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusEnum = Receita.StatusTransacao.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido: " + status + ". Use 'pago' ou 'pendente'.");
            }
        }
        
        // Cria ordenação
        Sort sort = criarOrdenacao(ordenacao, ordem);
        
        // Aplica filtros baseado nos parâmetros fornecidos
        if (categoria != null && dataInicio != null && dataFim != null && statusEnum != null) {
            return receitaRepository.findByIdCategoriaAndStatusAndDataMovBetween(categoria, statusEnum, dataInicio, dataFim, sort);
        } else if (categoria != null && statusEnum != null) {
            return receitaRepository.findByIdCategoriaAndStatus(categoria, statusEnum, sort);
        } else if (categoria != null && dataInicio != null && dataFim != null) {
            return receitaRepository.findByIdCategoriaAndDataMovBetween(categoria, dataInicio, dataFim, sort);
        } else if (statusEnum != null && dataInicio != null && dataFim != null) {
            return receitaRepository.findByStatusAndDataMovBetween(statusEnum, dataInicio, dataFim, sort);
        } else if (categoria != null) {
            return receitaRepository.findByIdCategoria(categoria, sort);
        } else if (statusEnum != null) {
            return receitaRepository.findByStatus(statusEnum, sort);
        } else if (dataInicio != null && dataFim != null) {
            return receitaRepository.findByDataMovBetween(dataInicio, dataFim, sort);
        } else {
            return receitaRepository.findAll(sort);
        }
    }
    
    private Sort criarOrdenacao(String ordenacao, String ordem) {
        // Define campo de ordenação
        String campo = "dataMov"; // padrão
        if ("valor".equalsIgnoreCase(ordenacao)) {
            campo = "valor";
        } else if ("data".equalsIgnoreCase(ordenacao)) {
            campo = "dataMov";
        }
        
        // Define direção da ordenação
        Sort.Direction direcao = Sort.Direction.DESC; // padrão
        if ("asc".equalsIgnoreCase(ordem)) {
            direcao = Sort.Direction.ASC;
        }
        
        return Sort.by(direcao, campo);
    }

    // --- Método para LER POR ID (GET) ---
    public Receita buscarReceitaPorId(Integer id) {
        // findById retorna um "Optional", que é um container
        // .orElseThrow() é uma forma elegante de dizer "ou me dê a Receita, ou lance um erro"
        return receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada com o id: " + id));
    }

    // --- Método para ATUALIZAR (PUT) ---
    public Receita atualizarReceita(Integer id, Receita receitaDetails) {
        // 1. Busca a receita no banco para ver se ela existe
        Receita receitaExistente = buscarReceitaPorId(id); // Reutiliza o método de busca

        // 2. Atualiza os dados da receita que veio do banco
        receitaExistente.setDescricao(receitaDetails.getDescricao());
        receitaExistente.setValor(receitaDetails.getValor());
        receitaExistente.setDataMov(receitaDetails.getDataMov());
        receitaExistente.setIdCategoria(receitaDetails.getIdCategoria());
        receitaExistente.setStatus(receitaDetails.getStatus());
        // (Não atualizamos o ID do usuário, geralmente)

        // 3. Salva as alterações (o .save() funciona como "update" se o objeto já tem um ID)
        return receitaRepository.save(receitaExistente);
    }

    // --- Método para DELETAR (DELETE) ---
    public void deletarReceita(Integer id) {
        // 1. Busca para garantir que existe antes de deletar
        Receita receitaParaDeletar = buscarReceitaPorId(id);

        // 2. Deleta
        receitaRepository.delete(receitaParaDeletar);
    }
}