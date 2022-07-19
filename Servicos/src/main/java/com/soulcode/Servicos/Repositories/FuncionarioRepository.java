package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository; // lembrar de verificar se tudo foi importado
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional; //sempre importar do Java util

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByEmail(String email);


    //possibilidade quando não tiver outra opção
   // @Query(value = "SELECT * FROM funcionario WHERE id_cargo =:idCargo", nativeQuery = true)
   // List<Funcionario> findByFuncionarioWhereCargo(Integer idCargo);

    List<Funcionario> findByCargo(Optional<Cargo> cargo);



} //Cria-se a interface e extend para a super classe JpaRepository<Classe extendida, tipo de Id>
  //Criar um método para procurar o email -  não é igual o findById q já vem pronto.
  // utiliza-se o método do Jpa findBy + atributo c letra maiuscula (tipo e parametro)
  // Optional<Java Class> findByNomeDoAtributo
  //Optional<Java CLass> findByNomeAndEmailAndFoto (string nome, String email, String foto); - para buscar mais coisas

  //Depois vai em FuncionarioService para criar o serviço para buscá-lo pelo seu e-mail


// AULA DO DIA 15/06/2022 -04/07/2022