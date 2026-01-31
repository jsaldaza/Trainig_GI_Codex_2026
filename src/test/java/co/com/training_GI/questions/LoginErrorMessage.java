package co.com.training_GI.questions;

import co.com.training_GI.ui.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class LoginErrorMessage implements Question<String> {

    public static LoginErrorMessage text() {
        return new LoginErrorMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        return LoginPage.ERROR_MESSAGE.resolveFor(actor).getText().trim();
    }
}
