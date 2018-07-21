package com.ronnye.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ronnye.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	
	@Transactional(readOnly=true)
	//@Query("Select obj from Estado obj where obj.nome LIKE %:nome% ORDER BY obj.nome :orderBy")
	public List<Estado> findAllByOrderByNome();


}
