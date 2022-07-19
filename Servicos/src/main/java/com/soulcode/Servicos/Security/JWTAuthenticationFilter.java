package com.soulcode.Servicos.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

// Essa classe entra em ação ao chamar /login
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    } // tenta autenticar e informa quando dar certo ou não


    //abaixo do construtor digita Attempt e completa para o método abaixo
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // deleta o retorno e cria o método de tentativa
        //tenta autenticar o usuário
        try {
            //{"login": "", "password": ""}
            //extrair informações de user da request "bruto"
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class); //readValue vai ler o q tem dentro da request
            return authenticationManager.authenticate( // chama a autenticação do spring
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(),
                            user.getPassword(),
                            new ArrayList<>()
                    )
            );
        }catch(IOException io){
            //caso o json da rqeuisição não bater com User.class
           throw new RuntimeException(io.getMessage());
        }
    }

    // abaixo do método acima digita suc e cria método succesfullAuthentication

    @Override // aqui ocorre a devolução, caso dê certo, ele vem p esse método e gera o token
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
       //deleta de super em diante
        //gerar o token e devolver para o usuário que se autenticou com sucesso
        AuthUserDetail user = (AuthUserDetail) authResult.getPrincipal(); //converte a class dentro dos parenteses p o retorno do getPrincipal é um object p ele entender q é da classe
        String token = jwtUtils.generateToken(user.getUsername()); //gera o token com o username - login (email)

        response.setHeader("Access-Control-Allow-Origin", "*"); //permite o acesso p o token
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        // {"Authorization": "<token>"}
        response.getWriter().write("{\"Authorization\" : \"" + token + "\"}");
        response.getWriter().flush(); //termina a escrita
    }

    //digita uns e completa o código de UnsuccessfulAuthentication q aparece
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //deleta o super em diante
        //costumizar a resposta de erro do login que falhou
        response.setStatus(401); //unauthorizaed
        response.setContentType("application/json"); // seta o arquivo para json
        response.getWriter().write(json()); //mensagem de erro no body | o append ou write pega o conteudo da string e joga p resposta
        response.getWriter().flush(); //termina a escrita
    }

    //estrutura do json montada conforme é recebida pelo postman e constuída anterioemente por Tati
    String json() { //formatar a mensagem de erro
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\" : \"Não autorizado\", "
                + "\"message\": \"Email/senha inválidos\""
                + "\"path\": \"/login\""
                + "}";
    }

}

/**
 * FRONT MANDA {"login": "jr@gmail.com", "password": "12345"}
 * A partir do JSON -> User
 * Tenta realizar autenticação
 *      Caso dê certo:
 *          - Gera o token JWT
 *          - Retorna o token para o FRONT
 */