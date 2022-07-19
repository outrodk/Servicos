package com.soulcode.Servicos.Services;
// Aqui se criará os métodos para realizar os crud (Cadastrar, editar, excluir)

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegratyViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // sempre verificar se as classes estão sendo importadas

//Quando se fala em serviços estamos falando dos métodos crud da tabela

@Service // Classe responsável pelos serviços da aplicação da tabela funcionário
public class FuncionarioService {

    // Aqui se faz a Injeção de depenencia
    @Autowired //  pega os metodos do FuncionarioRepository e libera para ser acessado por essa nova classe através do funcionarioRepository
    FuncionarioRepository funcionarioRepository;

    @Autowired
    CargoRepository cargoRepository;

    // primeiro serviço na tabela de funcionarios vai ser a leitura de todos os funcionarios cadastrados
    //findAll -> método do spring Data Jpa - busca todos os registros de uma tabela

    @Cacheable("funcionariosCache")
    public List<Funcionario> mostrarTodosFuncionarios(){
       return funcionarioRepository.findAll();
    }

    //vamos criar mais um serviço relacionado ao funcionario
    //criar um serviço de buscar apenas um funcionario pelo seu id(chave primária)

    @Cacheable(value = "funcionariosCache", key = "#idFuncionario")
    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                ()-> new EntityNotFoundException("Funcionário não cadastrado: " + idFuncionario)
        );
    }

    // Cria-se um optional da tabela Funcionario chamado funcionario que recebe os métodos do Jpa através do funcionarioRepository e buscar pelo findById a idFuncionario
    // o Optional evita o travamento ou queda da aplicação qnd da exception not found
    // caso ele não encontre o funcionário, ele retorna null
    // precisamos criar um endpoint para trazer um unico funcionario através do ID em funcionarioController

    //Criar mais um serviço para buscar um funcionário pelo seu email

    @Cacheable(value = "funcionariosCache", key = "#email")
    public Funcionario mostrarUmFuncionarioPeloEmail(String email){
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
                return funcionario.orElseThrow();
    }

    //Metodo p qnd n tiver a opção abaixo
    //public List<Funcionario> mostraFuncionarioPeloCargo(Integer idCargo){
       // return funcionarioRepository.findByFuncionarioWhereCargo(idCargo);
   // }

    @Cacheable(value = "funcionariosCache", key = "#idCargo")
    public List<Funcionario> mostrarTodosFuncionariosPeloCargo(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(cargo);
    }

    //Metodo para cadastrar um novo funcionario

    @CachePut(value = "funcionariosCache", key = "#funcionario.idFuncionario")
    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo) {
        //só por precaução, nós vamos colocar o id do funcionário como nulo
        try {
            Cargo cargo = cargoRepository.findById(idCargo).get();
            funcionario.setCargo(cargo);
            return funcionarioRepository.save(funcionario);
        }catch (Exception e){
            throw new DataIntegratyViolationException("Erro ao cadastrar funcionário");
        }
    }

    //Cria os métodos aqui e no controller a função de como vamos buscar


    //serico para excluir um funcionario
    //será tipo void pois ele n retornará nenhum funcionário
    @CacheEvict(value = "funcionariosCache", key = "#idFuncionario", allEntries = true)
    public void  excluirFuncionario(Integer idFuncionario){
        funcionarioRepository.deleteById(idFuncionario); //pega o método já existente no jpa deleteById

    }

    //Serviço para editar um funcionario

    @CachePut(value = "funcionariosCache", key = "#funcionario.idFuncionario")
    public Funcionario editarFuncionario(Funcionario funcionario){
        return funcionarioRepository.save(funcionario); // o funcionario é para mostrar q o id já existe
    }

    //serviço para adicionar foto

    @CachePut(value = "funcionariosCache", key = "#funcionario")
    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto){
        Funcionario funcionario = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }

    @CachePut(value = "funcionariosCache", key = "#funcionario, #idCargo")
    public Funcionario atribuirCargo(Funcionario funcionario, Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        funcionario.setCargo(cargo.get());
        return funcionarioRepository.save(funcionario);

    }



    //volta para UploadFileCOntroller para finalizar o processo



}
