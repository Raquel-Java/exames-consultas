package br.com.exames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.exames.entity.Exame;


@Repository
public interface ExameDao extends JpaRepository<Exame, Long> {
	
	   /* ------------------------------------------------------------------------------------------------------*/

 default Optional<Exame> consultarExamePorNome(String nome) {

     return this.findBynome(nome);
 }

 Optional<Exame> findBynome(String nome);

 /* ------------------------------------------------------------------------------------------------------*/

}
