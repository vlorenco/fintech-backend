package br.com.fiap.fintech.dto;

import java.math.BigDecimal;
import java.util.List;

public class GastosPorCategoriaDTO {
    
    private PeriodoDTO periodo;
    private List<CategoriaResumoDTO> categorias;
    
    public GastosPorCategoriaDTO() {}
    
    public GastosPorCategoriaDTO(PeriodoDTO periodo, List<CategoriaResumoDTO> categorias) {
        this.periodo = periodo;
        this.categorias = categorias;
    }
    
    public PeriodoDTO getPeriodo() { return periodo; }
    public void setPeriodo(PeriodoDTO periodo) { this.periodo = periodo; }
    public List<CategoriaResumoDTO> getCategorias() { return categorias; }
    public void setCategorias(List<CategoriaResumoDTO> categorias) { this.categorias = categorias; }
    
    public static class PeriodoDTO {
        private Integer mes;
        private Integer ano;
        private String tipo;
        
        public PeriodoDTO() {}
        public PeriodoDTO(Integer mes, Integer ano, String tipo) {
            this.mes = mes;
            this.ano = ano;
            this.tipo = tipo;
        }
        
        public Integer getMes() { return mes; }
        public void setMes(Integer mes) { this.mes = mes; }
        public Integer getAno() { return ano; }
        public void setAno(Integer ano) { this.ano = ano; }
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
    }
    
    public static class CategoriaResumoDTO {
        private Integer categoriaId;
        private String categoriaNome;
        private String categoriaCor;
        private BigDecimal total;
        private Long quantidade;
        private BigDecimal percentual;
        
        public CategoriaResumoDTO() {}
        
        public CategoriaResumoDTO(Integer categoriaId, String categoriaNome, String categoriaCor, 
                                  BigDecimal total, Long quantidade, BigDecimal percentual) {
            this.categoriaId = categoriaId;
            this.categoriaNome = categoriaNome;
            this.categoriaCor = categoriaCor;
            this.total = total;
            this.quantidade = quantidade;
            this.percentual = percentual;
        }
        
        public Integer getCategoriaId() { return categoriaId; }
        public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
        public String getCategoriaNome() { return categoriaNome; }
        public void setCategoriaNome(String categoriaNome) { this.categoriaNome = categoriaNome; }
        public String getCategoriaCor() { return categoriaCor; }
        public void setCategoriaCor(String categoriaCor) { this.categoriaCor = categoriaCor; }
        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }
        public Long getQuantidade() { return quantidade; }
        public void setQuantidade(Long quantidade) { this.quantidade = quantidade; }
        public BigDecimal getPercentual() { return percentual; }
        public void setPercentual(BigDecimal percentual) { this.percentual = percentual; }
    }
}