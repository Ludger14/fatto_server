package br.com.fatto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fatto.model.TaskModel;

public interface TaskRepository extends JpaRepository<TaskModel, Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM tarefas as tar \n" +
            " where tar.nome = :nome ")
	TaskModel checkNameTask(@Param("nome") String nome);
	
	@Query(nativeQuery = true, value = "SELECT * FROM tarefas ORDER BY ordem_apresentacao DESC")
    List<TaskModel> ordemApresentacaoDesc();
	
	@Query(nativeQuery = true, value = "SELECT * FROM tarefas ORDER BY ordem_apresentacao ASC")
    List<TaskModel> ordemApresentacaoAsc();
}
