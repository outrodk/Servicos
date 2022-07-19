package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> mostrarTodosClientes(){
        List<Cliente> clientes = clienteService.mostraTodosClientes();
        return clientes;
    }

    @GetMapping("/clientes/{idCliente}")
    public ResponseEntity<Cliente> mostrarUnClientePeloID(@PathVariable Integer idCliente){
            Cliente cliente = clienteService.mostrarUmClientePeloID(idCliente);
            return ResponseEntity.ok().body(cliente);
    }

    @GetMapping("/clientesEmail/{email}")
    public ResponseEntity<Cliente> mostrarUmClientePeloEmail(@PathVariable String email){
        Cliente cliente = clienteService.mostrarUmClientePeloEmail(email);
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente){
        cliente = clienteService.cadastrarCliente(cliente);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(cliente.getIdCliente()).toUri();
        return ResponseEntity.created(novaUri).body(cliente);
    }

    @DeleteMapping("/clientes/{idCliente}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Integer idCliente){
        clienteService.excluirCliente(idCliente);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clientes/{idCliente}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Integer idCliente, @RequestBody Cliente cliente){
        cliente.setIdCliente(idCliente);
        clienteService.editarCliente(cliente);
        return ResponseEntity.ok().body(cliente);
    }



}
