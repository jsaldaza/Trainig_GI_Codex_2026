package co.com.training_GI.support;

public class AppConfig {

    private AppConfig() {
    }

    public static String baseUrl() {
        return Config.get("webdriver.base.url", "https://www.saucedemo.com");
    }

    public static boolean sessionReuse() {
        return Boolean.parseBoolean(Config.get("app.session.reuse", "false"));
    }

    public static boolean navigationFallback() {
        return Boolean.parseBoolean(Config.get("app.navigation.fallback", "false"));
    }
}
