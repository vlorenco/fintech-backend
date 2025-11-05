package br.com.fiap.fintech.dto;

import java.math.BigDecimal;
import java.util.List;

public class GraficoMensalDTO {
    
    private List<DadosMensaisDTO> meses;
    
    public GraficoMensalDTO() {}
    
    public GraficoMensalDTO(List<DadosMensaisDTO> meses) {
        this.meses = meses;
    }
    
    public List<DadosMensaisDTO> getMeses() { return meses; }
    public void setMeses(List<DadosMensaisDTO> meses) { this.meses = meses; }
    
    public static class DadosMensaisDTO {
        private Integer mes;
        private Integer ano;
        private BigDecimal receitas;
        private BigDecimal despesas;
        private BigDecimal saldo;
        
        public DadosMensaisDTO() {}
        
        public DadosMensaisDTO(Integer mes, Integer ano, BigDecimal receitas, BigDecimal despesas, BigDecimal saldo) {
            this.mes = mes;
            this.ano = ano;
            this.receitas = receitas;
            this.despesas = despesas;
            this.saldo = saldo;
        }
        
        public Integer getMes() { return mes; }
        public void setMes(Integer mes) { this.mes = mes; }
        public Integer getAno() { return ano; }
        public void setAno(Integer ano) { this.ano = ano; }
        public BigDecimal getReceitas() { return receitas; }
        public void setReceitas(BigDecimal receitas) { this.receitas = receitas; }
        public BigDecimal getDespesas() { return despesas; }
        public void setDespesas(BigDecimal despesas) { this.despesas = despesas; }
        public BigDecimal getSaldo() { return saldo; }
        public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    }
}