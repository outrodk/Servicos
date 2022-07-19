package com.soulcode.Servicos.Controllers;


import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("servicos")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder; // vai no ServicosApplication e cria o @Bean (injeta métodos) - lembrar q depois o bean foi retirado de lá e posto em JWTConfig

    @GetMapping("/usuarios")
    public List<User> usuarios(){
        return userService.Listar();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<User> inserirUsuario(@RequestBody User user){
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        user = userService.cadastrar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
