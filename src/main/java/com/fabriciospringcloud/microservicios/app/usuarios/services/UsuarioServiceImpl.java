package com.fabriciospringcloud.microservicios.app.usuarios.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity.Usuario;
import com.fabriciospringcloud.microservicios.app.usuarios.clients.AuditoriaFeignClient;
import com.fabriciospringcloud.microservicios.app.usuarios.models.dao.UsuarioRepository;



@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private AuditoriaFeignClient clientAuditoria;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Usuario> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findById(Long id) {

		return repository.findById(id);
	}
	
	@Override
	@Transactional
	public Usuario save(Usuario entity) {

		return repository.save(entity);
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findByApellidoContaining(String params, Pageable pageable) {
		return repository.findByApellidoContaining(params, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAllByOrderByApellidoAsc(Pageable pageable) {
		return repository.findAllByOrderByApellidoAsc(pageable);
	}


	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		return repository.findByUsername(username);
	}

/*	
	public Usuario update(Usuario usuario, Long id) {
		return repository.update(usuario, id);
	}
*/	
	@Override
	public void AuditActualizarUsuario(String referencia) {
		clientAuditoria.actualizarUsuario(referencia);
	}
	
}// end
