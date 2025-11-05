package br.com.fiap.fintech.service;

import br.com.fiap.fintech.model.Meta;
import br.com.fiap.fintech.model.ContribuicaoMeta;
import br.com.fiap.fintech.dto.ContribuicaoHistoricoDTO;
import br.com.fiap.fintech.repository.MetaRepository;
import br.com.fiap.fintech.repository.ContribuicaoMetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private ContribuicaoMetaRepository contribuicaoMetaRepository;

    // --- CRIAR ---
    public Meta criarMeta(Meta meta) {
        if (meta.getDescricao() == null || meta.getDescricao().isBlank()) {
            throw new IllegalArgumentException("A descrição da meta é obrigatória.");
        }
        if (meta.getValorObjetivo() == null || meta.getValorObjetivo().doubleValue() <= 0) {
            throw new IllegalArgumentException("O valor da meta deve ser maior que zero.");
        }

        return metaRepository.save(meta);
    }

    // --- LER TODOS ---
    public List<Meta> listarTodasMetas() {
        return metaRepository.findAll();
    }

    // --- LER TODOS COM FILTROS ---
    public List<Meta> listarMetasComFiltros(String status, String categoria, String ordenacao, String ordem) {
        
        // Converte status string para enum
        Meta.StatusMeta statusEnum = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusEnum = Meta.StatusMeta.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido: " + status + ". Use 'ativa', 'pausada' ou 'concluida'.");
            }
        }
        
        // Cria ordenação
        Sort sort = criarOrdenacao(ordenacao, ordem);
        
        // Aplica filtros baseado nos parâmetros fornecidos
        if (statusEnum != null && categoria != null && !categoria.isEmpty()) {
            return metaRepository.findByStatusAndDescricaoContainingIgnoreCase(statusEnum, categoria, sort);
        } else if (statusEnum != null) {
            return metaRepository.findByStatus(statusEnum, sort);
        } else if (categoria != null && !categoria.isEmpty()) {
            return metaRepository.findByDescricaoContainingIgnoreCase(categoria, sort);
        } else {
            if ("progresso".equalsIgnoreCase(ordenacao)) {
                return "asc".equalsIgnoreCase(ordem) ? 
                    metaRepository.findAllOrderByProgressoAsc() : 
                    metaRepository.findAllOrderByProgressoDesc();
            }
            return metaRepository.findAll(sort);
        }
    }
    
    private Sort criarOrdenacao(String ordenacao, String ordem) {
        // Define campo de ordenação
        String campo = "dataMeta"; // padrão
        if ("valor".equalsIgnoreCase(ordenacao)) {
            campo = "valorObjetivo";
        } else if ("dataLimite".equalsIgnoreCase(ordenacao)) {
            campo = "dataMeta";
        } else if ("progresso".equalsIgnoreCase(ordenacao)) {
            // Para progresso, usar queries customizadas (implementadas no repository)
            return Sort.unsorted(); // Será tratado especialmente no método de filtro
        }
        
        // Define direção da ordenação
        Sort.Direction direcao = Sort.Direction.DESC; // padrão
        if ("asc".equalsIgnoreCase(ordem)) {
            direcao = Sort.Direction.ASC;
        }
        
        return Sort.by(direcao, campo);
    }

    // --- LER POR ID ---
    public Meta buscarMetaPorId(Integer id) {
        return metaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meta não encontrada com o id: " + id));
    }

    // --- ATUALIZAR ---
    public Meta atualizarMeta(Integer id, Meta metaDetails) {
        Meta metaExistente = buscarMetaPorId(id);

        metaExistente.setDescricao(metaDetails.getDescricao());
        metaExistente.setValorObjetivo(metaDetails.getValorObjetivo());
        metaExistente.setValorAtual(metaDetails.getValorAtual());
        metaExistente.setDataMeta(metaDetails.getDataMeta());
        metaExistente.setUsuario(metaDetails.getUsuario());
        metaExistente.setStatus(metaDetails.getStatus());

        return metaRepository.save(metaExistente);
    }

    // --- DELETAR ---
    public void deletarMeta(Integer id) {
        Meta meta = buscarMetaPorId(id);
        metaRepository.delete(meta);
    }

    // --- CONTRIBUIR PARA META ---
    public Meta contribuirParaMeta(Integer id, BigDecimal valor, String descricao) {
        Meta meta = buscarMetaPorId(id);
        
        // Validações
        if (meta.getStatus() != Meta.StatusMeta.ATIVA) {
            throw new IllegalArgumentException("Só é possível contribuir para metas ativas.");
        }
        
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da contribuição deve ser positivo.");
        }
        
        // Salva valores anteriores para histórico
        BigDecimal valorAnterior = meta.getValorAtual();
        BigDecimal novoValor = valorAnterior.add(valor);
        
        // Atualiza meta
        meta.setValorAtual(novoValor);
        
        // Verifica se meta foi atingida
        if (novoValor.compareTo(meta.getValorObjetivo()) >= 0) {
            meta.setStatus(Meta.StatusMeta.CONCLUIDA);
        }
        
        // Salva contribuição no histórico
        ContribuicaoMeta contribuicao = new ContribuicaoMeta(valor, descricao, meta, valorAnterior, novoValor);
        contribuicaoMetaRepository.save(contribuicao);
        
        return metaRepository.save(meta);
    }
    
    // --- HISTÓRICO DE CONTRIBUIÇÕES ---
    public List<ContribuicaoHistoricoDTO> obterHistoricoContribuicoes(Integer metaId) {
        Meta meta = buscarMetaPorId(metaId); // Verifica se meta existe
        List<ContribuicaoMeta> contribuicoes = contribuicaoMetaRepository.findByMetaIdOrderByDataContribuicaoDesc(metaId);
        
        // Converte para DTO para evitar referência circular
        return contribuicoes.stream()
                .map(c -> new ContribuicaoHistoricoDTO(
                    c.getId(),
                    c.getValor(),
                    c.getDescricao(),
                    c.getDataContribuicao(),
                    c.getValorAnterior(),
                    c.getValorAtual(),
                    meta.getId(),
                    meta.getDescricao()
                ))
                .collect(Collectors.toList());
    }
    
    // --- PAUSAR META ---
    public Meta pausarMeta(Integer id) {
        Meta meta = buscarMetaPorId(id);
        
        if (meta.getStatus() != Meta.StatusMeta.ATIVA) {
            throw new IllegalArgumentException("Só é possível pausar metas ativas.");
        }
        
        meta.setStatus(Meta.StatusMeta.PAUSADA);
        return metaRepository.save(meta);
    }
    
    // --- REATIVAR META ---
    public Meta reativarMeta(Integer id) {
        Meta meta = buscarMetaPorId(id);
        
        if (meta.getStatus() != Meta.StatusMeta.PAUSADA) {
            throw new IllegalArgumentException("Só é possível reativar metas pausadas.");
        }
        
        meta.setStatus(Meta.StatusMeta.ATIVA);
        return metaRepository.save(meta);
    }
}
