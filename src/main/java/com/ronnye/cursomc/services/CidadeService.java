package com.ronnye.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ronnye.cursomc.domain.Cidade;
import com.ronnye.cursomc.repositories.CidadeRepository;
import com.ronnye.cursomc.services.exceptions.DataIntegrityException;
import com.ronnye.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;
	
	public Cidade find(Integer id) {
		Optional<Cidade> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}
	
	public Cidade insert(Cidade obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Cidade  update(Cidade obj) {
		Cidade newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Cidade> findAll(Integer estado_id){
		return repo.findCidades(estado_id);
	}
	
	public Page<Cidade> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	private void updateData(Cidade newObj, Cidade obj) {
		newObj.setNome(obj.getNome());
	}
	
}
