package com.soulcode.Servicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableCaching // habilita a aplicação para utilizar cache - comente se o redis não esteja instalado ainda
@SpringBootApplication
public class ServicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicosApplication.class, args);
		// System.out.println(new BCryptPasswordEncoder().encode("batata")); // Depois que colocar para rodar ele irá criar o código hash, que será necessário para insserir manualmente o primeiro usuário no SQL ou no arquivo ImportSQL no resources
	}



}
