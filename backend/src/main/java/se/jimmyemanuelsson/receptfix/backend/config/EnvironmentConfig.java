package se.jimmyemanuelsson.receptfix.backend.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static void loadEnvVariables() {
        System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
    }
}
