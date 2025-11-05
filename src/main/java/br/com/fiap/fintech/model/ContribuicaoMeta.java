package br.com.fiap.fintech.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_fin_contribuicao_meta")
public class ContribuicaoMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribuicao")
    private Integer id;

    @Column(name = "vl_contribuicao", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "ds_descricao", length = 200)
    private String descricao;

    @Column(name = "dt_contribuicao", nullable = false)
    private LocalDateTime dataContribuicao;

    @Column(name = "vl_anterior", precision = 15, scale = 2)
    private BigDecimal valorAnterior;

    @Column(name = "vl_atual", precision = 15, scale = 2)
    private BigDecimal valorAtual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_meta", nullable = false)
    @JsonBackReference
    private Meta meta;

    // --- Construtores ---
    public ContribuicaoMeta() {
    }

    public ContribuicaoMeta(BigDecimal valor, String descricao, Meta meta, BigDecimal valorAnterior, BigDecimal valorAtual) {
        this.valor = valor;
        this.descricao = descricao;
        this.meta = meta;
        this.valorAnterior = valorAnterior;
        this.valorAtual = valorAtual;
        this.dataContribuicao = LocalDateTime.now();
    }

    // --- Getters e Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataContribuicao() {
        return dataContribuicao;
    }

    public void setDataContribuicao(LocalDateTime dataContribuicao) {
        this.dataContribuicao = dataContribuicao;
    }

    public BigDecimal getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(BigDecimal valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}