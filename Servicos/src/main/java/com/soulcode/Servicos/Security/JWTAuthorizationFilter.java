package com.soulcode.Servicos.Security;

import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

//utilizado qnd for acontecer uma requisição
//entra em ação em toda endpoint que está protegida
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private JWTUtils jwtUtils;
    public  JWTAuthorizationFilter(AuthenticationManager manager, JWTUtils jwtUtils){
        super(manager);
        this.jwtUtils = jwtUtils;
    }

    //digita dof e completa a função doFilterInternal
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
       //deleta do super em diante
        String token = request.getHeader("Authorization"); // o tokien virá do angular assim: Bearer huahui116736721aa -> Beater token
        if (token != null && token.startsWith("Bearer")){ //token válido
            // concluir autorização
            UsernamePasswordAuthenticationToken authToken = getAuthentication(token.substring(7)); // pega o token q começa a partir do indice 7
            if(authToken != null){
                SecurityContextHolder.getContext().setAuthentication(authToken);
                //SecurityContextHolder.getContext().getAuthentication();
                // O context Guarda informações do usuário autenticado no contexto do Spring
                // Essa informação pode ser utilizada dentro dos controllers da aplicação
            }
        }
        chain.doFilter(request,response);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        String login = jwtUtils.getLogin(token); //extrai o login do subject
        if(login == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(login, null, new ArrayList<>());
    }
}
