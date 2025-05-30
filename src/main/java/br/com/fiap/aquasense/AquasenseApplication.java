package br.com.fiap.aquasense;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "API CodeCrafters", description = "API de mapeamento de desastres naturais.", version = "v1"))
public class AquasenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AquasenseApplication.class, args);
	}

}
