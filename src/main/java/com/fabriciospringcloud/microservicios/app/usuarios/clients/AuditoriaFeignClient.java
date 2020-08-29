package com.fabriciospringcloud.microservicios.app.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="microservicios-auditoria")
public interface AuditoriaFeignClient {

	@PostMapping("/editar-usuario")
	public void actualizarUsuario(@RequestParam String referencia);
	
}
