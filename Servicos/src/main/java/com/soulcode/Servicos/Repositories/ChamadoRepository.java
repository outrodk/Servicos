package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Chamado; // Lembrar de importar o Chamado
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository; // Verificar se foi importado
import org.springframework.data.jpa.repository.Query;

import java.security.Provider;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

    List<Chamado> findByCliente(Optional<Cliente> cliente);

    List<Chamado> findByFuncionario(Optional<Funcionario> funcionario);

    //Por ser um enum o status será diferente
    // o caminho é semelhante o do MySQL
    @Query(value = "SELECT * FROM chamado WHERE status =:status",nativeQuery = true)
    List<Chamado> findByStatus(String status);

    @Query(value = "SELECT * FROM chamado WHERE data_entrada BETWEEN :data1 AND :data2", nativeQuery = true)
    List<Chamado> findByIntervaloData(Date data1, Date data2);




}

// AGora vai criar o serviço no ChamadoService

//sempre que precisar fazer uma nova querry, testa antes no workbench