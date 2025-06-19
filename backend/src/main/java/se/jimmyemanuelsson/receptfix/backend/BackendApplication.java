package se.jimmyemanuelsson.receptfix.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.jimmyemanuelsson.receptfix.backend.config.DotenvInitializer;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BackendApplication.class);
		app.addInitializers(new DotenvInitializer());
		app.run(args);
	}
}

