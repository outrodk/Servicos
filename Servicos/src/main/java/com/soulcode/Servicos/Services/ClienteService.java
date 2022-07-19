package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Cacheable("clientesCache") // só chama o return se o cache expirar clientesCache:: []
    public List<Cliente> mostraTodosClientes(){
        return clienteRepository.findAll();
    }

    //findById - busca um cliente especifico pelo seu id
    @Cacheable(value = "clientesCache", key = "#idCliente") // clienteCache::1
    public Cliente mostrarUmClientePeloID(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return cliente.orElseThrow(
                ()-> new EntityNotFoundException("Cliente não cadastrado: " + idCliente)
        );
    }

    @Cacheable(value = "clientesCache", key = "#email")
    public Cliente mostrarUmClientePeloEmail(String email){
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        return cliente.orElseThrow();
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente cadastrarCliente(Cliente cliente){
        //por precaução vamos limpar o campo do id do cliente
        cliente.setIdCliente(null);
        return clienteRepository.save(cliente);
    }

    @CacheEvict(value = "clientesCache", key = "#idCliente", allEntries = true)
    public void excluirCliente(Integer idCliente){
        mostrarUmClientePeloID(idCliente);
        clienteRepository.deleteById(idCliente);
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente") // atualiza(substitui) a info do cache de acordo com a key
    public Cliente editarCliente(Cliente cliente){
        mostrarUmClientePeloID(cliente.getIdCliente());
        return clienteRepository.save(cliente);
    }



}
