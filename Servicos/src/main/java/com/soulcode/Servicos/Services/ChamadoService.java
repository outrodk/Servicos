package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.*;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    // findAll (método da Spring Data) - busca todos os registros


    @Cacheable("chamadoCache")
    public List<Chamado> mostrarTodosChamados(){
        return chamadoRepository.findAll();
    }

    // findById - busca um registro pela sua chave primária
    @Cacheable(value = "chamadoCache", key = "#idChamado")
    public Chamado mostrarUmChamado(Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        return chamado.orElseThrow(
                ()-> new EntityNotFoundException("Chamado não cadastrado: " + idChamado)
        );
    }

    @Cacheable(value = "chamadoCache", key = "#idCliente")
    public List<Chamado> buscarChamadosPeloCliente(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }

    @Cacheable(value = "chamadoCache", key = "#idFuncionario")
    public List<Chamado> buscarChamadoPeloFuncionario(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }

    @Cacheable(value = "chamadoCache", key = "#status")
    public List<Chamado> buscarChamadosPeloStatus(String status){
        return chamadoRepository.findByStatus(status);
    }

    @Cacheable(value = "chamadoCache", key = "T(java.util.Objects).hash(#data1, #data2)")
    public List<Chamado> buscarPorIntervaloData(Date data1, Date data2){
        return chamadoRepository.findByIntervaloData(data1, data2);
    }

    //Cadastrar um novo chamado
    //temos 2 regras:
    //  1) no momento do cadastro do chamado, já devemos informar de qual cliente é
    //  2) no momento do cadastro do chamado, a principio vamos fazer esse cadastro sem estar atribuido a um funcionario
    //  3) no momento do cadastro do chamado, o status desse chamado deve ser RECEBIDO

    //serviço para cadastro de novo chamado
    @CachePut(value = "chamadoCache", key = "#idCliente")
    public Chamado cadastrarChamado(Chamado chamado, Integer idCliente){
        //regra 3 - atribuição do status recebido para o chamado que está sendo cadastrado
        chamado.setStatus(StatusChamado.RECEBIDO);
        //regra 2 - dizer que ainda não atribuímos esse chamado a nenhum funcionário
        chamado.setFuncionario(null); //utiliza o null pq ainda n vai ter nenhum, funcionário atribuido
        //regra 1 - buscando os dados do cliente dono do chamado
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);
    }

    //Metodo para exclusão de um chamado
    @CacheEvict(value = "chamadoCache", key = "#idChamado")
    public void excluirChamado(Integer idChamado){
        chamadoRepository.deleteById(idChamado);
    }

    //Método para editar um chamado
    //No momento da edição de um chamado, devemos preservar o cliente e o funcionario desse chamado
    //Vamos editar os dados do chamado, mas continuamos com os dados do cliente e os dados do funcionário

    @CachePut(value = "chamadoCache", key = "#idChamado")
    public Chamado editarChamado(Chamado chamado, Integer idChamado){
        //instaciamos aqui um objeto do tipo Chamado para guardar os dados do chamado sem as novas alterações
        Chamado chamadoSemAsNovasAlteracoes = mostrarUmChamado(idChamado);
        Funcionario funcionario = chamadoSemAsNovasAlteracoes.getFuncionario();
        Cliente cliente = chamadoSemAsNovasAlteracoes.getCliente();

        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);
        return chamadoRepository.save(chamado);
    }

    //Método para atribuir um funcionário para um determinado chamado
    //ou trocar um funcionário de determinado chamado
    //Regra: no momento em que um deter,inado é atribuido a um funcionário, o status do chamado precisa ser alterado para ATRIBUIDO

    @CachePut(value = "chamadoCache", key = "#idFuncionario")
    public Chamado atribuirFuncionario(Integer idChamado, Integer idFuncionario){
        // buscar os dados do funcionário que vai ser atibuído a esse chamado
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        // buscar o chamado para o qual vai ser especificado o funcionário escolhido
        Chamado chamado = mostrarUmChamado(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);
        return chamadoRepository.save(chamado);
    }

    //método para modificar o status de um chamado

    @CachePut(value = "chamadoCache", key = "#status")
    public Chamado modificarStatus(Integer idChamado,String status){
        Chamado chamado = mostrarUmChamado(idChamado);
        if (chamado.getFuncionario() != null){
            switch (status){
                case "ATRIBUIDO":
                {
                    chamado.setStatus(StatusChamado.ATRIBUIDO);
                    break;
                }
                case "CONCLUIDO":
                {
                    chamado.setStatus(StatusChamado.CONCLUIDO);
                    break;
                }
                case "ARQUIVADO":
                {
                    chamado.setStatus(StatusChamado.ARQUIVADO);
                    break;
                }

            }
        }

        switch (status){

            case "RECEBIDO":
            {
                chamado.setStatus(StatusChamado.RECEBIDO);
                break;
            }
        }
        return chamadoRepository.save(chamado);
     }






    //construir o controller desse metodo para só ser ATRIBUIDO se tiver funcionário


    // AGORA VAI NO CONTROLLER MAPEAR ESSE SERVIÇO
}