package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @GetMapping("/pagamentos")
    public List<Pagamento> mostrarTodosPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.mostrarTodosPagamentos();
        return pagamentos;
    }

    @GetMapping("pagamentos/{idPagamento}")
    public ResponseEntity<Pagamento> mostrarUmPagamento(@PathVariable Integer idPagamento){
        Pagamento pagamento = pagamentoService.mostrarUmPagamento(idPagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @GetMapping("pagamentosPeloStatus")
    public List<Pagamento> buscarPagamentosPeloStatus(@RequestParam("statusPagamento") String statusPagamento){
        List<Pagamento> pagamentos = pagamentoService.buscarPagamentosPeloStatus(statusPagamento);
        return pagamentos;
    }

    @GetMapping("/pagamentosChamadosComCliente")
    public List<List> orcamentoComServicoCliente(){
        List<List> pagamentos = pagamentoService.orcamentoComServicoCliente();
        return pagamentos;
    }

    @PostMapping("/pagamentos/{idChamado}")
    public ResponseEntity<Pagamento>cadastrarPagamento(@PathVariable Integer idChamado,
                                                       @RequestBody Pagamento pagamento){
        pagamento = pagamentoService.cadastrarPagamento(pagamento, idChamado);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pagamento.getIdPagamento()).toUri();
        return ResponseEntity.created(novaUri).build();
    }

    @PutMapping("/pagamentos/{idPagamento}")
    public ResponseEntity<Pagamento> editarPagamento(@PathVariable Integer idPagamento,
                                                     @RequestBody Pagamento pagamento){
        pagamento.setIdPagamento(idPagamento);
        pagamentoService.editarPagamento(pagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @PutMapping("/pagamentosAlteracaoStatus/{idPagamento}")
    public ResponseEntity<Pagamento> modificarStatusPagamento(@PathVariable Integer idPagamento,
                                                              @RequestParam ("statusPagamento") String statusPagamento){
        pagamentoService.modificarStatusPagamento(idPagamento, statusPagamento);
        return ResponseEntity.noContent().build();

    }

}
