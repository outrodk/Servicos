package com.soulcode.Servicos.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//Abstrai o User do banco para que o Security conheça seus dados
//ou conhecer o usuário melhor
public class AuthUserDetail implements UserDetails { // implementar os metodos na lâmpada

    //cria os atributos e depois um construtor
    private String login;
    private String password;

    // n precisará dos getters and setters

    public AuthUserDetail(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //coleção de papeis, autoridades do usuário
        return new ArrayList<>(); //deleta o null e retorna um novo arrayList vazio
    }

    @Override
    public String getPassword() {
        return password; // ao invés de retornar nulo, informa que a senha estará dentro do password
    }

    @Override
    public String getUsername() {
        return login; // ao invés de retornar nulo, informa o que o usuário  estará dentro do login
    }

    @Override
    public boolean isAccountNonExpired() { // a conta não expirou
        return true; // troca para true para permitir q os usuários consigam logar
    }

    @Override
    public boolean isAccountNonLocked() { // a conta não bloqueou
        return true; // troca para true
    }

    @Override
    public boolean isCredentialsNonExpired() { // as credenciais não expiraram
        return true; // troca para true
    }

    @Override
    public boolean isEnabled() { // o usuário não está habilitado
        return true; // troca para true
    } //o -> desabilitado, 1-> habilitado

}

/*
 * O Spring Security não se comunica diretamente com o nosso model User =(
 * Então devemos criar uma classe que ele conheça para fazer essa comunicação,
 * UserDetails = Guarda informações do contexto de autenticação do usuário (autorizações, habilitado, etc)
 * */

