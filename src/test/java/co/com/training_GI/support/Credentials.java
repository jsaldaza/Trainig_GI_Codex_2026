package co.com.training_GI.support;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public class Credentials {

    private static final EnvironmentVariables ENV = SystemEnvironmentVariables.createEnvironmentVariables();

    private Credentials() {
    }

    public static String username() {
        return value("app.username");
    }

    public static String password() {
        return value("app.password");
    }

    private static String value(String key) {
        String fromSys = System.getProperty(key);
        if (fromSys != null && !fromSys.isBlank()) {
            return fromSys;
        }
        String envKey = key.toUpperCase().replace('.', '_');
        String fromEnv = System.getenv(envKey);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        String fromConf = EnvironmentSpecificConfiguration.from(ENV).getProperty(key);
        if (fromConf == null || fromConf.isBlank()) {
            throw new IllegalStateException("Missing config for " + key);
        }
        return fromConf;
    }
}
