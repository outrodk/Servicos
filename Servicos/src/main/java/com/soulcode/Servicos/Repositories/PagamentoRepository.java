package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    @Query(value = "SELECT * FROM pagamento WHERE status_pagamento =:statusPagamento",nativeQuery = true)
    List<Pagamento> findByStatusPagamento(String statusPagamento);

    //Chama uma Lista de Lista pq é uma lista de 3 lista que vão ser chamadas
    @Query(value = "SELECT pagamento.*, chamado.id_chamado, chamado.titulo, cliente.id_cliente, cliente.nome \n" +
            "FROM chamado RIGHT JOIN pagamento ON chamado.id_chamado = pagamento.id_pagamento  \n" +
            "LEFT JOIN cliente ON cliente.id_cliente = chamado.id_cliente;\n",nativeQuery = true)
    List<List> orcamentoComServicoCliente();

}
