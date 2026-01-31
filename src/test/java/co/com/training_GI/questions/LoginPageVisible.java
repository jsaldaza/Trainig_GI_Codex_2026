package co.com.training_GI.questions;

import co.com.training_GI.ui.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class LoginPageVisible implements Question<Boolean> {

    public static LoginPageVisible isDisplayed() {
        return new LoginPageVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return LoginPage.USERNAME.resolveFor(actor).isVisible();
    }
}
