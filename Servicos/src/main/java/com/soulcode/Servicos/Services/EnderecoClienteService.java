package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.EnderecoCliente;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.EnderecoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoClienteService {

    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public List<EnderecoCliente> mostrarTodosEnderecos() { return enderecoClienteRepository.findAll();}

    @Cacheable("enderecoCache")
    public EnderecoCliente mostrarUmEnderecoPeloId(Integer idEndereco){
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(idEndereco);
        return enderecoCliente.orElseThrow();
    }

    // CADASTRO DE UM NOVO ENDEREÇO
    // regras 1 -> para cadastrar um endereço, o cliente já deve estar cadastrado no database
    //        2 -> no momento do cadastro do endereço, precisamos cadastrar o id do cliente dono desse endereço
    //        3 -> o id do endereço vai ser o mesmo id do cliente
    //        4 -> Não permitir que um endereço seja salvo sem a existência do respectivo cliente

    @CachePut(value = "enderecoCache", key = "#idCliente")
    public EnderecoCliente cadastrarEnderecoCLiente(EnderecoCliente enderecoCliente, Integer idCliente) throws Exception {
        // Estamos declarando um Optional de cliente e atribuindo para este os dados do cliente que receberá o novo endereço
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.isPresent()){

        // o endereço precisa saber de que cliente ele é
        enderecoCliente.setIdEndereco(idCliente);
        enderecoClienteRepository.save(enderecoCliente);


        cliente.get().setEnderecoCliente(enderecoCliente);
        clienteRepository.save(cliente.get());
        return enderecoCliente;
        }else{
            throw new Exception();
        }
    }

    @CachePut(value = "enderecoCache", key = "#cliente.idCliente")
    public EnderecoCliente editarEndereco (EnderecoCliente enderecoCliente){
        return enderecoClienteRepository.save(enderecoCliente);
    }
}

// AULA DO DIA 05/07/2022
