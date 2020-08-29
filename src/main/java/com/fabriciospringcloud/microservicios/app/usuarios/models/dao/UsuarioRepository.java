package com.fabriciospringcloud.microservicios.app.usuarios.models.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity.Role;
import com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity.Usuario;


public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {


	public Page<Usuario> findByApellidoContaining(String params, Pageable pageable);

	public Page<Usuario> findAllByOrderByApellidoAsc(Pageable pageable);
	
	public Page<Role> findAllByOrderByNombreAsc(Pageable pageable);
	
	public Usuario findByUsername(String username);
	
	//public Usuario update(Usuario usuario, Long id);

}

