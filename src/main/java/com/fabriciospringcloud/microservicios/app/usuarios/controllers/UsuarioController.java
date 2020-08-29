package com.fabriciospringcloud.microservicios.app.usuarios.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity.Role;
import com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity.Usuario;
import com.fabriciospringcloud.microservicios.app.usuarios.services.UsuarioService;
//import org.springframework.core.env.Environment;
////import java.util.HashMap;
////import java.util.Map;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@RestController
public class UsuarioController {
	
	private static Logger log = LoggerFactory.getLogger(UsuarioController.class);
//	
//	@Autowired
//	private Environment env;
	
//	@Value("${configuracion.texto}")
//	private String texto;
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	

	//Feign oauth
	@PutMapping("/usuarios/{id}")
	public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id) {

		Optional<Usuario> o = service.findById(id);
		Usuario usuarioDb = o.get();
		if (o.isPresent()) {
			usuarioDb.setNombre(usuario.getNombre());
			usuarioDb.setApellido(usuario.getApellido());
			usuarioDb.setDependencia(usuario.getDependencia());
			usuarioDb.setEmail(usuario.getEmail());
			usuarioDb.setUsername(usuario.getUsername());
			service.save(usuarioDb);
		}
		return usuarioDb;
	}	
	
	//Feign oauth
	@GetMapping("/buscar-usuario")
	public Usuario findByUsername(String username){
		return service.findByUsername(username);
	}		
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(service.findAll());
	}	
	
	@GetMapping("pagina")
	public ResponseEntity<?> listar(Pageable pageable){
		return ResponseEntity.ok().body(service.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Usuario> o = service.findById(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(o.get());
	}

	@PostMapping
	public ResponseEntity<?> crear(@Validated @RequestBody Usuario entity, BindingResult result){
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Usuario entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo "+ err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	@PostMapping("/crear-usuario")
	public ResponseEntity<?> crearUsuario(@Validated @RequestBody Usuario usuario, BindingResult result){
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		String password = usuario.getPassword();
		String passwordBCrypt = passwordEncoder.encode(password);
		usuario.setPassword(passwordBCrypt);
		
		Usuario usuarioDb = service.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDb);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Validated @RequestBody Usuario usuario, BindingResult result, 
			                        @PathVariable Long id){
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Usuario> o = service.findById(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Usuario usuarioDb = o.get();
		usuarioDb.setNombre(usuario.getNombre());
		usuarioDb.setApellido(usuario.getApellido());
		usuarioDb.setDependencia(usuario.getDependencia());
		usuarioDb.setEmail(usuario.getEmail());
		usuarioDb.setUsername(usuario.getUsername());
		service.save(usuarioDb);
		
		log.info("Registrando auditoria editar usuario sin foto"+o.get().getUsername()+"...");
		service.AuditActualizarUsuario("editar usuario sin foto");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDb);
		
	}
	
	
	@GetMapping("/filtrar/{params}")
	public ResponseEntity<?> filtrarPorApellido(@PathVariable String params, Pageable pageable){
		return ResponseEntity.ok().body(service.findByApellidoContaining(params, pageable));
	}
	
	@GetMapping("/listar")
	public ResponseEntity<?> findAllByOrderByApellidoAsc(Pageable pageable){
		return ResponseEntity.ok().body(service.findAllByOrderByApellidoAsc(pageable));
	}
		
	
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Usuario usuario, BindingResult result, 
			@RequestParam MultipartFile archivo) throws IOException {
		if(!archivo.isEmpty()) {
			usuario.setFoto(archivo.getBytes());
		}
		String password = usuario.getPassword();
		String passwordBCrypt = passwordEncoder.encode(password);
		usuario.setPassword(passwordBCrypt);
		return this.crear(usuario, result);
	}
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid Usuario usuario, BindingResult result, @PathVariable Long id,
			@RequestParam MultipartFile archivo) throws IOException{
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Usuario> o = service.findById(id);
		
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Usuario usuarioDb = o.get();
		usuarioDb.setNombre(usuario.getNombre());
		usuarioDb.setApellido(usuario.getApellido());
		usuarioDb.setDependencia(usuario.getDependencia());
		usuarioDb.setEmail(usuario.getEmail());
		usuarioDb.setUsername(usuario.getUsername());
		
		if(!archivo.isEmpty()) {
			usuarioDb.setFoto(archivo.getBytes());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDb));
	}
	
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id){
	
		Optional<Usuario> o = service.findById(id);
		
		if(!o.isPresent() || o.get().getFoto() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Resource imagen = new ByteArrayResource(o.get().getFoto());
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(imagen);
	}	
	
	
	@PutMapping("/{id}/asignar-roles")
	public ResponseEntity<?> asignarRoles(@RequestBody List<Role> roles, @PathVariable Long id){
	    Optional<Usuario> o = service.findById(id);
	    if(!o.isPresent()) {
	    	return ResponseEntity.notFound().build();
	    }
	    Usuario dbUsuario = o.get();
	    
	    dbUsuario.getRoles().clear();
	    
	    roles.forEach(r -> {
	    	dbUsuario.addRole(r);
	    });
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbUsuario));
	}
	
	
	@PutMapping("/{id}/eliminar-role")
	public ResponseEntity<?> eliminarRole(@RequestBody Role role, @PathVariable Long id){
	    Optional<Usuario> o = this.service.findById(id);
	    if(!o.isPresent()) {
	    	return ResponseEntity.notFound().build();
	    }
	    Usuario dbUsuario = o.get();
	    
	    dbUsuario.removeRole(role);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbUsuario));
	}
	
	
	@PutMapping("/{id}/activar")
	public ResponseEntity<?> activar(@RequestBody Usuario usuario, @PathVariable Long id){
	    Optional<Usuario> o = service.findById(id);
	    if(!o.isPresent()) {
	    	return ResponseEntity.notFound().build();
	    }
	    Usuario dbUsuario = o.get();
	    boolean estado = dbUsuario.getEnabled();
	    
	    if (estado) {
	    	dbUsuario.setEnabled(false);
	    }else {
	    	dbUsuario.setEnabled(true);
	    }

	    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbUsuario));
	}
	

	@PutMapping("/{id}/password")
	public ResponseEntity<?> restablecerPassword(@RequestBody Usuario usuario, @PathVariable Long id){

		Optional<Usuario> o = service.findById(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		String password = usuario.getPassword();
		String passwordBCrypt = passwordEncoder.encode(password);
		usuario.setPassword(passwordBCrypt);
		
		Usuario usuarioDb = o.get();
		usuarioDb.setPassword(usuario.getPassword());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDb));
		
	}

}//end
