package br.com.fiap.fintech.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_FIN_RECEITA")
public class Receita {

    @Id // 4. MARCA COMO CHAVE PRIMÁRIA
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECEITA")
    private Integer idReceita;

    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "ID_CATEGORIA")
    private Integer idCategoria;

    @Column(name = "DS_DESCRICAO", length = 200, nullable = false)
    private String descricao;

    @Column(name = "VL_VALOR", nullable = false)
    private BigDecimal valor;

    @Column(name = "DT_MOV")
    private LocalDate dataMov;

    @Enumerated(EnumType.STRING)
    @Column(name = "ST_STATUS", length = 20)
    private StatusTransacao status = StatusTransacao.PENDENTE;


    public enum StatusTransacao {
        PAGO, PENDENTE
    }


    public Receita() {
    }


    public Receita(Integer idUsuario, Integer idCategoria, String descricao, BigDecimal valor, LocalDate dataMov) {
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.descricao = descricao;
        this.valor = valor;
        this.dataMov = dataMov;
    }


    public Integer getIdReceita() {
        return this.idReceita;
    }

    public void setIdReceita(Integer idReceita) {
        this.idReceita = idReceita;
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataMov() {
        return this.dataMov;
    }

    public void setDataMov(LocalDate dataMov) {
        this.dataMov = dataMov;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Receita{" +
                "idReceita=" + idReceita +
                ", idUsuario=" + idUsuario +
                ", idCategoria=" + idCategoria +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", dataMov=" + dataMov +
                '}';
    }
}