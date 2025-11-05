package br.com.fiap.fintech.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_fin_meta")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_meta")
    private Integer id;

    @Column(name = "ds_descricao", nullable = false, length = 200)
    private String descricao;

    @Column(name = "vl_objetivo", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorObjetivo;

    @Column(name = "vl_atual", precision = 15, scale = 2)
    private BigDecimal valorAtual = BigDecimal.ZERO;

    @Column(name = "dt_meta")
    private LocalDate dataMeta;

    @Column(name = "id_usuario")
    private Integer usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status", length = 20)
    private StatusMeta status = StatusMeta.ATIVA;

    @OneToMany(mappedBy = "meta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ContribuicaoMeta> contribuicoes;


    public enum StatusMeta {
        ATIVA, PAUSADA, CONCLUIDA
    }

    // --- Construtores ---
    public Meta() {
    }

    public Meta(String descricao, BigDecimal valorObjetivo, BigDecimal valorAtual, LocalDate dataMeta, Integer usuario) {
        this.descricao = descricao;
        this.valorObjetivo = valorObjetivo;
        this.valorAtual = valorAtual;
        this.dataMeta = dataMeta;
        this.usuario = usuario;
    }

    // --- Getters e Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorObjetivo() {
        return valorObjetivo;
    }

    public void setValorObjetivo(BigDecimal valorObjetivo) {
        this.valorObjetivo = valorObjetivo;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public LocalDate getDataMeta() {
        return dataMeta;
    }

    public void setDataMeta(LocalDate dataMeta) {
        this.dataMeta = dataMeta;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public StatusMeta getStatus() {
        return status;
    }

    public void setStatus(StatusMeta status) {
        this.status = status;
    }

    public List<ContribuicaoMeta> getContribuicoes() {
        return contribuicoes;
    }

    public void setContribuicoes(List<ContribuicaoMeta> contribuicoes) {
        this.contribuicoes = contribuicoes;
    }


    public BigDecimal getProgresso() {
        if (valorObjetivo == null || valorObjetivo.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return valorAtual
                .multiply(BigDecimal.valueOf(100))
                .divide(valorObjetivo, 2, BigDecimal.ROUND_HALF_UP);
    }
}
