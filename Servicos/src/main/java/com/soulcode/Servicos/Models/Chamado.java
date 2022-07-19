package com.soulcode.Servicos.Models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Chamado {

    @Id
    private Integer idChamado;

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @Column(nullable = false) // não pode cadastrar novos chamados sem um titulo
    private String titulo;

    @Column(nullable = true)
    private String descricao;

    @DateTimeFormat(pattern = "dd-MM-yyyy") //pattern = tipo, padrão da data
    @Column(columnDefinition = "date", nullable = false)
    private Date dataEntrada;

    // Apos criar o enum StatusChamado volta aqui e instancia otipo do private
    @Enumerated(EnumType.STRING) //indica q o status é um Enum
    private StatusChamado status;

    @ManyToOne  //relacionando tabelas (muitos para um) - muitos chamados para um funcionario
    @JoinColumn(name = "idFuncionario") //informa qual coluna da tabela de funcionarios vai se relacionar com a de chamados
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "idCliente") // o name é uma forma de dar um nome a coluna
    private Cliente cliente; //faz a construção da coluna

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPagamento", unique = true)
    private Pagamento pagamento;

    //AGORA VAI NAS TABELAS DE CLIENTES DE FUNCIONARIOS E CLIENTES PARA REFERENCIAR OS CHAMADOS

    public Integer getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(Integer idChamado) {
        this.idChamado = idChamado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}