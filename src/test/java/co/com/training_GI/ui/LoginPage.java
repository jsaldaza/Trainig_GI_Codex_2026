package co.com.training_GI.ui;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.annotations.DefaultUrl;

@DefaultUrl("https://www.saucedemo.com")
public class LoginPage extends PageObject {

    public static final Target USERNAME = Target.the("username field")
            .locatedBy("#user-name");

    public static final Target PASSWORD = Target.the("password field")
            .locatedBy("#password");

    public static final Target LOGIN_BUTTON = Target.the("login button")
            .locatedBy("#login-button");

    public static final Target ERROR_MESSAGE = Target.the("login error message")
            .locatedBy("[data-test='error'], .error-message-container");
}
