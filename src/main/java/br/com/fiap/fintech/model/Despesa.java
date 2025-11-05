package br.com.fiap.fintech.model;

import jakarta.persistence.*; // Importando as anotações
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_FIN_DESPESA")
public class Despesa {

    @Id // Marca como Chave Primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DESPESA") // Nome da coluna
    private Integer idDespesa;

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


    public Despesa() {}


    public Despesa(Integer idUsuario, Integer idCategoria, String descricao, BigDecimal valor, LocalDate dataMov) {
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.descricao = descricao;
        this.valor = valor;
        this.dataMov = dataMov;
    }


    public Integer getIdDespesa() { return idDespesa; }
    public void setIdDespesa(Integer idDespesa) { this.idDespesa = idDespesa; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDate getDataMov() { return dataMov; }
    public void setDataMov(LocalDate dataMov) { this.dataMov = dataMov; }

    public StatusTransacao getStatus() { return status; }
    public void setStatus(StatusTransacao status) { this.status = status; }


    @Override public String toString() {
        return "Despesa{id=" + idDespesa + ", usuario=" + idUsuario + ", cat=" + idCategoria +
                ", desc='" + descricao + "', valor=" + valor + ", data=" + dataMov + "}";
    }
}