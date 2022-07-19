package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Models.StatusPagamento;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PagamentoService {


    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    @Cacheable("pagamentoCache")
    public List<Pagamento> mostrarTodosPagamentos(){
        return pagamentoRepository.findAll();
    }

    @Cacheable(value = "pagamentoCache", key = "#idPagamento")
    public Pagamento mostrarUmPagamento(Integer idPagamento){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    @Cacheable(value = "pagamentoCache", key = "statusPagamento")
    public List<Pagamento> buscarPagamentosPeloStatus(String statusPagamento){
        return pagamentoRepository.findByStatusPagamento(statusPagamento);
    }

    @CachePut(value = "pagamentoCache", key = "#idChamado")
    public Pagamento cadastrarPagamento(Pagamento pagamento, Integer idChamado){
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()){
            pagamento.setIdPagamento(idChamado);
            pagamento.setStatusPagamento(StatusPagamento.LANCADO);
            pagamentoRepository.save(pagamento);
            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());
            return pagamento;
        } else {
            throw new RuntimeException();
        }
    }

    @CachePut(value = "pagamentoCache", key = "#pagamento")
    public Pagamento editarPagamento(Pagamento pagamento){
        return pagamentoRepository.save(pagamento);
}

    //Verificar fazendo pelo statusPagamento
    @CachePut(value = "pagamentoCache", key = "#idPagamento" )
    public Pagamento modificarStatusPagamento(Integer idPagamento, String statusPagamento){
        Pagamento pagamento = mostrarUmPagamento(idPagamento);
        switch(statusPagamento){
            case "LANCADO":
                pagamento.setStatusPagamento(StatusPagamento.LANCADO);
            case "QUITADO":
                pagamento.setStatusPagamento(StatusPagamento.QUITADO);
                break;
        }
        return pagamentoRepository.save(pagamento);
    }

    @Cacheable("pagamentoCache")
    public List<List> orcamentoComServicoCliente(){
        return pagamentoRepository.orcamentoComServicoCliente();

    }

}





