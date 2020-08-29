package com.fabriciospringcloud.microservicios.app.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;



/*Este microservicio utiliza los entity de la libreria de usuario commons.
 * Copiamos el groupId, artifactId y la version del pom.xml de usuarios commons para agregarla como dependencia
 * en el pom.xml de usuarios:
 * 		<dependency>
			<groupId>com.fabriciospringcloud.microservicios.app.commons</groupId>
			<artifactId>microservicios-commons</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
 * 
 * Con la anotacion @EntityScan({"com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity"})
 * hacemos que el contexto de persistencia de JPA pueda encontrar los entity.
 * 
 * 
 * 
 * 
 */
@EnableFeignClients
@SpringBootApplication
@EntityScan({"com.fabriciospringcloud.microservicios.app.commons.usuarios.models.entity"})
//@EnableJpaRepositories("com.fabriciospringcloud.microservicios.app.usuarios.models.dao")
//@EntityScan(basePackageClasses={Usuario.class, Role.class})
public class MicroserviciosUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosUsuariosApplication.class, args);
	}

}
