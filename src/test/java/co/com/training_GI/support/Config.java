package co.com.training_GI.support;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public final class Config {

    private static final EnvironmentVariables ENV = SystemEnvironmentVariables.createEnvironmentVariables();

    private Config() {
    }

    public static String get(String key, String defaultValue) {
        String value = getOptional(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    public static String getRequired(String key) {
        String value = getOptional(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing config for " + key);
        }
        return value;
    }

    private static String getOptional(String key) {
        String fromSys = System.getProperty(key);
        if (fromSys != null && !fromSys.isBlank()) {
            return fromSys;
        }
        String envKey = key.toUpperCase().replace('.', '_');
        String fromEnv = System.getenv(envKey);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        return EnvironmentSpecificConfiguration.from(ENV).getOptionalProperty(key).orElse(null);
    }
}
