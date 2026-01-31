package co.com.training_GI.support;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public class AppConfig {

    private static final EnvironmentVariables ENV = SystemEnvironmentVariables.createEnvironmentVariables();

    private AppConfig() {
    }

    public static String baseUrl() {
        return value("webdriver.base.url", "https://www.saucedemo.com");
    }

    public static boolean sessionReuse() {
        return Boolean.parseBoolean(value("app.session.reuse", "false"));
    }

    private static String value(String key, String defaultValue) {
        String fromSys = System.getProperty(key);
        if (fromSys != null && !fromSys.isBlank()) {
            return fromSys;
        }
        String envKey = key.toUpperCase().replace('.', '_');
        String fromEnv = System.getenv(envKey);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        String fromConf = EnvironmentSpecificConfiguration.from(ENV).getOptionalProperty(key).orElse(null);
        if (fromConf == null || fromConf.isBlank()) {
            return defaultValue;
        }
        return fromConf;
    }
}
