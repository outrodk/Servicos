package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Services.FuncionarioService;
import com.soulcode.Servicos.Util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin // evita erros de core
@RestController
@RequestMapping("servicos")
public class UploadFileController {

    @Autowired //cria o @Autowired para fazer a injeção de dependência do FuncionarioService
    FuncionarioService funcionarioService;

    @PostMapping("/funcionarios/envioFotos/{idFuncionario}")
    public ResponseEntity<Void> enviarFoto(@PathVariable Integer idFuncionario, //o Id pela URL
                                           MultipartFile file,  //arquivo que se quer enviar (form-data no postman)
                                           @RequestParam("nome") String nome){ //nome que se quer dar ao arquivo através dos parametros da requisição

        String fileName = nome;
        String uploadDir = "D:/TIAGO/SoulCode/SPRING/fotoFunc";
        String nomeMaisCaminho = "D:/TIAGO/SoulCode/SPRING/fotoFunc/" + nome; //opção uploadDir + nome

        //Vai em funcionarioService para criar o serviço de adicionar uma foto e volta aqui para finalizar


        try{
            UploadFile.saveFile(uploadDir, fileName, file);
            funcionarioService.salvarFoto(idFuncionario, nomeMaisCaminho); //Aqui vc salva na tabela de funcionarios
        } catch (IOException e) {
            System.out.println(("O arquivo não foi enviado: " + e.getMessage()));
        }
        return ResponseEntity.ok().build();

    }


}
