package br.com.fiap.fintech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "T_FIN_CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA")
    private Integer idCategoria;

    @Column(name = "ID_USUARIO", nullable = false)
    private Integer idUsuario;

    @Column(name = "NM_CATEGORIA", length = 100, nullable = false)
    private String nome;

    @Column(name = "DS_COR", length = 7)
    private String cor;


    public Categoria() {}


    public Categoria(Integer idUsuario, String nome, String cor) {
        this.idUsuario = idUsuario; this.nome = nome; this.cor = cor;
    }


    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    @Override public String toString() {
        return "Categoria{id=" + idCategoria + ", usuario=" + idUsuario + ", nome='" + nome + "', cor='" + cor + "'}";
    }
}