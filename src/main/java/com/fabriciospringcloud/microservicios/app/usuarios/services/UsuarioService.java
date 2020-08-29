package com.fabriciospringcloud.microservicios.app.usuarios.services;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity.Usuario;


public interface UsuarioService {
	
	public Iterable<Usuario> findAll();
	
	public Page<Usuario> findAll(Pageable pageable);
	
	public Optional<Usuario> findById(Long id);
	
	public Usuario save(Usuario entity);
	
	public void deleteById(Long id);
	
	public Usuario findByUsername(String username);
	
	//public Usuario update(Usuario usuario, Long id);
	
	public Page<Usuario> findAllByOrderByApellidoAsc(Pageable pageable);
	
	public Page<Usuario> findByApellidoContaining(String params, Pageable pageable);
	
	public void AuditActualizarUsuario(String referencia);
	
}
