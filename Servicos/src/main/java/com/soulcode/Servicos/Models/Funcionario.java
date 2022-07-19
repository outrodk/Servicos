package com.soulcode.Servicos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Funcionario {

    @Id // mostra q Id será a chave
    @GeneratedValue(strategy = GenerationType.IDENTITY) // diz q ele é autoincremento (gera o Id para o funcionário)
    private Integer idFuncionario;

    @Column(nullable = false, length = 100) // indica que faz parte daq coluna, diz q n pode ser nulo e o número máximo de caracteres é 100
    private String nome;

    @Column(nullable = false, length = 100, unique = true) // unique diz q n pode existir dois funcionarios com o mesmo email
    private String email;

    @Column(nullable = true) // pode-se cadastrar o funcionário sem foto (por isso true)
    private String foto;

    //ADVINDO DOS CHAMADOS< VAMOS CRIAR O RELACIONAMENTO ENTRE TABELAS e cria os get and setters

    @JsonIgnore //para n criar loop
    @OneToMany(mappedBy = "funcionario") // um funcionario para vários chamados
    private List<Chamado> chamados = new ArrayList<Chamado>();


    //a primeira ´parte da anotação do relacionamento diz respeito a tabela que estamos inserindo o relacionamento(funcionario
    //a segunda parte da anotação do relacionamento diz respeito a segunda tabela a qual esta vai relacionar
    @ManyToOne
    @JoinColumn(name = "idCargo")
    private Cargo cargo;



    //AGORA VAI PARA O CLIENTE CRIAR A REFERENCIA DE CONEXÃO A TABELA DE CHAMADOS

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
