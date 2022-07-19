package com.soulcode.Servicos.Config;

import com.soulcode.Servicos.Security.JWTAuthenticationFilter;
import com.soulcode.Servicos.Security.JWTAuthorizationFilter;
import com.soulcode.Servicos.Services.AuthUserDetailService;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Agrega todos as informações de segurança http e gerência do user
@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter { // vai guardar a maioria das configurações de segurança que fizemos até agora
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthUserDetailService userDetailsService;

    //digita conf e pega os dois métodos abaixo
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       //deletar de super em diante

        //UserDetailService -> Carregar o usuário do banco
        //BCrypt -> gerador de hash de senhas
        //usa o passwordEncoder() para comparar senhas de login
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable(); //habilita o cors e desabilita o csrf
        //JWTAuthenticationFilter
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils));// a partir daqui o security reconhece a autenticação
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtils));// a partir daqui o security reconhece a aurorização

        //permite o login, mas qualquer outra requisição precisa ser autenticada
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll() //habilitou o método post no login, caso queira fazer outro é só seguir esse método
               //antMatchers(HttpMethod.GET, "/Servicos/**").permitALL() // todos os endpoints q obdecerem esse padrão poderiam utilizar o método POST - mas o post n será nessa função - os ** permitem qualquer endpoint existente ali
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // toda vez necessitará mandar o token para acessar a aplicação

    }
    //necessita configurar os metodoss para a configuração do cors
    @Bean // cross origin resource sharing
    CorsConfigurationSource corsConfigurationSource() { // configuração global do cors
        CorsConfiguration configuration = new CorsConfiguration(); // puxa as configurações padrões
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));
        //métodos permitidos para o front acessar
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //busca a configuração e aplica os endpoints de acordo com o padrão após a barra
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }
    // "/servicos/funcionarios" -> "/** -> Todos os endpoints



    @Bean //retorno de um método
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // o passwordEncoder é o gerador de senha

}
