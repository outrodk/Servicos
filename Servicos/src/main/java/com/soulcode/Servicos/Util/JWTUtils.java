package com.soulcode.Servicos.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils { // gerenciar e gerar tokens

    @Value("${jwt.secret}") //pega o valor do secret do applicationproperties e injeta aqui dentro p ele n ficar exposto
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration; // Long é maior q o Integer

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email) //withSubject - a quem se refere esse token, o usuário(email)
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + expiration) //newDate gera valor da data em milisegundos
                ).sign(Algorithm.HMAC512(secret));
    }

    public String getLogin(String token){
        return JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(token)
                .getSubject(); //vai tirar de dentro do token o sujeito (email/login)
    }
}
