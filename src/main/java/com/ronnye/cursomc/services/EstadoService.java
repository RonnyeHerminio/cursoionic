package com.ronnye.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ronnye.cursomc.domain.Estado;
import com.ronnye.cursomc.dto.EstadoDTO;
import com.ronnye.cursomc.repositories.EstadoRepository;
import com.ronnye.cursomc.services.exceptions.DataIntegrityException;
import com.ronnye.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;
	
	public Estado find(Integer id) {
		Optional<Estado> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName()));
	}
	
	public Estado insert(Estado obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Estado  update(Estado obj) {
		Estado newObj = find(obj.getId());
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
	
	public List<Estado> findAll(){
		return repo.findAllByOrderByNome();
	}
	
	public Page<Estado> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Estado fromDTO(EstadoDTO objDTO) {
		return new Estado(objDTO.getId(), objDTO.getNome());
	}
	
	private void updateData(Estado newObj, Estado obj) {
		newObj.setNome(obj.getNome());
	}
	
}
