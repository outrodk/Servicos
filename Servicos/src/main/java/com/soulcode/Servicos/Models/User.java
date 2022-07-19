package com.soulcode.Servicos.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto incremento
    private Integer id;
    @Column(unique = true, nullable = false) // cada usuário precisa ter um email diferente e o campo n pode ser nulo
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // p a senha não aparecer na coluna utiliza-se essa propriedade. Qnd for transformado em json essa propriedade n vai junto
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

