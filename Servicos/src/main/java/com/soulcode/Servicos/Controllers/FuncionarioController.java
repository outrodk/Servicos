package com.soulcode.Servicos.Controllers;


import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Services.CargoService;
import com.soulcode.Servicos.Services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@CrossOrigin // impede o erro de aplicações utilizando diferentes portas intervenham p acessar os endpoints
@RestController // API do tipo Rest - responsável pela camada de controle. Cada endpoint precisa ser independente um do outro
@RequestMapping("servicos") // mapeamento das requisicoes. Todos os endpoints irão começar por servicos
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    CargoService cargoService;

    @GetMapping("/funcionarios")
    public List<Funcionario> mostrarTodosFuncionarios(){
       List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionarios();
       return funcionarios;
    }
    // para pegar os dados coloca a rota que será de /funcionarios

    // criando rota a partir de um ID que passar
    @GetMapping("/funcionarios/{idFuncionario}") //as chaves dentro da rota significa q depois da barra vai ser passado um número (ID) a qual se quer buscar
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloId(@PathVariable Integer idFuncionario){
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloId(idFuncionario);
        return ResponseEntity.ok().body(funcionario);
    }

    // dentro do mostraFuncionariosPeloId precisa colocar o @PathVariable que vai ser mostrado pela url da requisição
    //ResponseEntity nos traz o cabeçalho e o corpo e o status da resposta
    // Retorna um ResponsyEntity, caso dê ok, retorna o corpo (body) dos dados dos funcionários


    //lembrar do caminho funcionariosEmail/ que é diferente do anterior qnd colocar no local host
    @GetMapping("/funcionariosEmail/{email}")
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloId(@PathVariable String email){
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloEmail(email);
          return ResponseEntity.ok().body(funcionario);
    }

    @GetMapping("/funcionariosDoCargo/{idCargo}")
    public List<Funcionario> mostrarTodosFuncionariosPeloCargo(@PathVariable Integer idCargo){
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionariosPeloCargo(idCargo);
        return funcionarios;
    }

    //Para cadastrar é o metodo Post
    //poderia ser outra rota q não /funcionarios mas como um é get e outro é post, n tem problema
    @PostMapping("/funcionarios/{idCargo}")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@PathVariable Integer idCargo, @RequestBody Funcionario funcionario) {
        // nessa linha abaixo(52) o funcionário já é salvo na tabela do database, agora precisamos criar uma uri para esse novo registro na tabela
        funcionario = funcionarioService.cadastrarFuncionario(funcionario, idCargo);
        //cria-se a nova uri a partir da classe URI
        //ServletUriComponentsBuilder é uma classe estática, n precisa ser instanciada, serve para criar uma nova Uri, uma nova rota do fromRequest que é do tipo post e o caminho é  /funcionarios e soma ao id com o .path
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id")
                //constroi e expande o funcionario pelo seu Id e acrescenta esse id na rota transformando a uri. exemplo: funcionarios/31
                .buildAndExpand(funcionario.getIdFuncionario()).toUri();
        return ResponseEntity.created(novaUri).body(funcionario);

    }

    //RequestBody é qnd um objeto da classe Funcionário usa os dados do funcionario esta passando pelo corpo da requisição (endpoint)


    //Para deletar usa o @deleteMapping e no caminho é o mesmo sendo que acrescido da impressão do idFuncionario
    @DeleteMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer idFuncionario){ //@Pathvariable para passar na url da requisição
        funcionarioService.excluirFuncionario(idFuncionario);
        return ResponseEntity.noContent().build(); //retorna no content pq foi uma exclusão, após fazer o get novamente ele aparecerá editado
    }

    //Editar um funcionário vindo lá do service

    @PutMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuincionario(@PathVariable Integer idFuncionario, @RequestBody Funcionario funcionario){
        funcionario.setIdFuncionario(idFuncionario); //usa o set p mostrar q o id já existe e q ele vai pegar e n mudá-lo, só editá-lo
        funcionarioService.editarFuncionario(funcionario);
        return ResponseEntity.ok().body(funcionario);   // mostra o q foi mudado no return
    }


    @PutMapping("/atribuirCargo/{idFuncionario}")
    public ResponseEntity<Funcionario> atribuirCargo(
            @PathVariable Integer idFuncionario,
            @RequestParam("idCargo") Integer idCargo,
            @RequestBody Funcionario funcionario){
        funcionario.setIdFuncionario(idFuncionario);

        funcionarioService.atribuirCargo(funcionario, idCargo);
        return ResponseEntity.ok().body(funcionario);
    }

    //Método para qnd n possuir o método construído lá em cima

    //@GetMapping("/funcionariosCargos/{idCargo}")
    //public List<Funcionario> mostraFuncionarioPeloCargo(@PathVariable Integer idCargo ){
      //  List<Funcionario> funcionarios = funcionarioService.mostraFuncionarioPeloCargo(idCargo);
       // return funcionarios;
    //}



}
