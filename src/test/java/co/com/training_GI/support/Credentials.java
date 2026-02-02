package co.com.training_GI.support;

public class Credentials {

    private Credentials() {
    }

    public static String username() {
        return Config.getRequired("app.username");
    }

    public static String password() {
        return Config.getRequired("app.password");
    }
}
