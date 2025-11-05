package br.com.fiap.fintech.dto;

import java.math.BigDecimal;

public class ResumoFinanceiroDTO {
    
    private PeriodoDTO periodo;
    private ResumoCategoriaDTO receitas;
    private ResumoCategoriaDTO despesas;
    private SaldoDTO saldo;
    private ResumoMetasDTO metas;
    
    // Construtores
    public ResumoFinanceiroDTO() {}
    
    public ResumoFinanceiroDTO(PeriodoDTO periodo, ResumoCategoriaDTO receitas, 
                               ResumoCategoriaDTO despesas, SaldoDTO saldo, ResumoMetasDTO metas) {
        this.periodo = periodo;
        this.receitas = receitas;
        this.despesas = despesas;
        this.saldo = saldo;
        this.metas = metas;
    }
    
    // Getters e Setters
    public PeriodoDTO getPeriodo() { return periodo; }
    public void setPeriodo(PeriodoDTO periodo) { this.periodo = periodo; }
    
    public ResumoCategoriaDTO getReceitas() { return receitas; }
    public void setReceitas(ResumoCategoriaDTO receitas) { this.receitas = receitas; }
    
    public ResumoCategoriaDTO getDespesas() { return despesas; }
    public void setDespesas(ResumoCategoriaDTO despesas) { this.despesas = despesas; }
    
    public SaldoDTO getSaldo() { return saldo; }
    public void setSaldo(SaldoDTO saldo) { this.saldo = saldo; }
    
    public ResumoMetasDTO getMetas() { return metas; }
    public void setMetas(ResumoMetasDTO metas) { this.metas = metas; }
    
    // Classes internas para DTOs
    public static class PeriodoDTO {
        private Integer mes;
        private Integer ano;
        
        public PeriodoDTO() {}
        public PeriodoDTO(Integer mes, Integer ano) {
            this.mes = mes;
            this.ano = ano;
        }
        
        public Integer getMes() { return mes; }
        public void setMes(Integer mes) { this.mes = mes; }
        public Integer getAno() { return ano; }
        public void setAno(Integer ano) { this.ano = ano; }
    }
    
    public static class ResumoCategoriaDTO {
        private BigDecimal total;
        private Long quantidade;
        private BigDecimal totalPago;
        private BigDecimal totalPendente;
        
        public ResumoCategoriaDTO() {}
        public ResumoCategoriaDTO(BigDecimal total, Long quantidade, BigDecimal totalPago, BigDecimal totalPendente) {
            this.total = total;
            this.quantidade = quantidade;
            this.totalPago = totalPago;
            this.totalPendente = totalPendente;
        }
        
        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }
        public Long getQuantidade() { return quantidade; }
        public void setQuantidade(Long quantidade) { this.quantidade = quantidade; }
        public BigDecimal getTotalPago() { return totalPago; }
        public void setTotalPago(BigDecimal totalPago) { this.totalPago = totalPago; }
        public BigDecimal getTotalPendente() { return totalPendente; }
        public void setTotalPendente(BigDecimal totalPendente) { this.totalPendente = totalPendente; }
    }
    
    public static class SaldoDTO {
        private BigDecimal atual;
        private BigDecimal projetado;
        
        public SaldoDTO() {}
        public SaldoDTO(BigDecimal atual, BigDecimal projetado) {
            this.atual = atual;
            this.projetado = projetado;
        }
        
        public BigDecimal getAtual() { return atual; }
        public void setAtual(BigDecimal atual) { this.atual = atual; }
        public BigDecimal getProjetado() { return projetado; }
        public void setProjetado(BigDecimal projetado) { this.projetado = projetado; }
    }
    
    public static class ResumoMetasDTO {
        private Long ativas;
        private Long concluidas;
        private BigDecimal progressoMedio;
        
        public ResumoMetasDTO() {}
        public ResumoMetasDTO(Long ativas, Long concluidas, BigDecimal progressoMedio) {
            this.ativas = ativas;
            this.concluidas = concluidas;
            this.progressoMedio = progressoMedio;
        }
        
        public Long getAtivas() { return ativas; }
        public void setAtivas(Long ativas) { this.ativas = ativas; }
        public Long getConcluidas() { return concluidas; }
        public void setConcluidas(Long concluidas) { this.concluidas = concluidas; }
        public BigDecimal getProgressoMedio() { return progressoMedio; }
        public void setProgressoMedio(BigDecimal progressoMedio) { this.progressoMedio = progressoMedio; }
    }
}