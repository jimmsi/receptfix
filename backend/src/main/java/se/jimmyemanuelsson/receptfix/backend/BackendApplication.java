package se.jimmyemanuelsson.receptfix.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.jimmyemanuelsson.receptfix.backend.config.EnvironmentConfig;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		EnvironmentConfig.loadEnvVariables();
		SpringApplication.run(BackendApplication.class, args);
	}
}

