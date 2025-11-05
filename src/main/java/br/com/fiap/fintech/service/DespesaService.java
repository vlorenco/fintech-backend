package br.com.fiap.fintech.service;

import br.com.fiap.fintech.model.Despesa;
import br.com.fiap.fintech.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service //
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    // --- CRIAR ---
    public Despesa criarDespesa(Despesa despesa) {

        if (despesa.getValor() == null || despesa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da despesa deve ser positivo.");
        }


        return despesaRepository.save(despesa);
    }

    // --- LER TODOS ---
    public List<Despesa> listarTodasDespesas() {
        return despesaRepository.findAll();
    }

    // --- LER TODOS COM FILTROS ---
    public List<Despesa> listarDespesasComFiltros(Integer categoria, LocalDate dataInicio, LocalDate dataFim, 
                                                   String status, String ordenacao, String ordem) {
        
        // Converte status string para enum
        Despesa.StatusTransacao statusEnum = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusEnum = Despesa.StatusTransacao.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido: " + status + ". Use 'pago' ou 'pendente'.");
            }
        }
        
        // Cria ordenação
        Sort sort = criarOrdenacao(ordenacao, ordem);
        
        // Aplica filtros baseado nos parâmetros fornecidos
        if (categoria != null && dataInicio != null && dataFim != null && statusEnum != null) {
            return despesaRepository.findByIdCategoriaAndStatusAndDataMovBetween(categoria, statusEnum, dataInicio, dataFim, sort);
        } else if (categoria != null && statusEnum != null) {
            return despesaRepository.findByIdCategoriaAndStatus(categoria, statusEnum, sort);
        } else if (categoria != null && dataInicio != null && dataFim != null) {
            return despesaRepository.findByIdCategoriaAndDataMovBetween(categoria, dataInicio, dataFim, sort);
        } else if (statusEnum != null && dataInicio != null && dataFim != null) {
            return despesaRepository.findByStatusAndDataMovBetween(statusEnum, dataInicio, dataFim, sort);
        } else if (categoria != null) {
            return despesaRepository.findByIdCategoria(categoria, sort);
        } else if (statusEnum != null) {
            return despesaRepository.findByStatus(statusEnum, sort);
        } else if (dataInicio != null && dataFim != null) {
            return despesaRepository.findByDataMovBetween(dataInicio, dataFim, sort);
        } else {
            return despesaRepository.findAll(sort);
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

    // --- LER POR ID ---
    public Despesa buscarDespesaPorId(Integer id) {

        return despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada com o id: " + id));
    }

    // --- ATUALIZAR ---
    public Despesa atualizarDespesa(Integer id, Despesa despesaDetails) {
        // 1. Acha o original
        Despesa despesaExistente = buscarDespesaPorId(id);

        // 2. Atualiza os campos
        despesaExistente.setDescricao(despesaDetails.getDescricao());
        despesaExistente.setValor(despesaDetails.getValor());
        despesaExistente.setDataMov(despesaDetails.getDataMov());
        despesaExistente.setIdCategoria(despesaDetails.getIdCategoria());
        despesaExistente.setIdUsuario(despesaDetails.getIdUsuario()); // Não esqueci desse
        despesaExistente.setStatus(despesaDetails.getStatus());

        // 3. Salva
        return despesaRepository.save(despesaExistente);
    }

    // --- DELETAR ---
    public void deletarDespesa(Integer id) {
        Despesa despesaParaDeletar = buscarDespesaPorId(id); // Vê se existe
        despesaRepository.delete(despesaParaDeletar); // Se existe, apaga
    }
}