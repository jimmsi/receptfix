package se.jimmyemanuelsson.receptfix.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.nio.file.Paths;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        Dotenv dotenv = Dotenv.configure()
                .directory(Paths.get("").toAbsolutePath().toString())
                .ignoreIfMissing()
                .load();

        setIfPresent(dotenv, "SPRING_DATASOURCE_URL");
        setIfPresent(dotenv, "POSTGRES_USER");
        setIfPresent(dotenv, "POSTGRES_PASSWORD");
        setIfPresent(dotenv, "JWT_SECRET");
        setIfPresent(dotenv, "JWT_EXPIRATION_MS");
    }

    private void setIfPresent(Dotenv dotenv, String key) {
        String value = dotenv.get(key);
        if (value != null) System.setProperty(key, value);
    }
}
