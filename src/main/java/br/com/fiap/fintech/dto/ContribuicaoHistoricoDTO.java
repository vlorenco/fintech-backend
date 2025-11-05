package br.com.fiap.fintech.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContribuicaoHistoricoDTO {
    
    private Integer id;
    private BigDecimal valor;
    private String descricao;
    private LocalDateTime dataContribuicao;
    private BigDecimal valorAnterior;
    private BigDecimal valorAtual;
    private MetaResumoDTO meta;
    
    // Construtor vazio
    public ContribuicaoHistoricoDTO() {}
    
    // Construtor completo
    public ContribuicaoHistoricoDTO(Integer id, BigDecimal valor, String descricao, LocalDateTime dataContribuicao, 
                                   BigDecimal valorAnterior, BigDecimal valorAtual, Integer metaId, String metaDescricao) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.dataContribuicao = dataContribuicao;
        this.valorAnterior = valorAnterior;
        this.valorAtual = valorAtual;
        this.meta = new MetaResumoDTO(metaId, metaDescricao);
    }
    
    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public LocalDateTime getDataContribuicao() { return dataContribuicao; }
    public void setDataContribuicao(LocalDateTime dataContribuicao) { this.dataContribuicao = dataContribuicao; }
    
    public BigDecimal getValorAnterior() { return valorAnterior; }
    public void setValorAnterior(BigDecimal valorAnterior) { this.valorAnterior = valorAnterior; }
    
    public BigDecimal getValorAtual() { return valorAtual; }
    public void setValorAtual(BigDecimal valorAtual) { this.valorAtual = valorAtual; }
    
    public MetaResumoDTO getMeta() { return meta; }
    public void setMeta(MetaResumoDTO meta) { this.meta = meta; }
    
    // Classe interna para resumo da meta
    public static class MetaResumoDTO {
        private Integer idMeta;
        private String descricao;
        
        public MetaResumoDTO() {}
        
        public MetaResumoDTO(Integer idMeta, String descricao) {
            this.idMeta = idMeta;
            this.descricao = descricao;
        }
        
        public Integer getIdMeta() { return idMeta; }
        public void setIdMeta(Integer idMeta) { this.idMeta = idMeta; }
        
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
    }
}