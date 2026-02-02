package co.com.training_GI.questions;

import co.com.training_GI.ui.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import java.time.Duration;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageVisible implements Question<Boolean> {

    public static LoginPageVisible isDisplayed() {
        return new LoginPageVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(10));
        try {
            wait.until(driver -> !LoginPage.USERNAME.resolveAllFor(actor).isEmpty()
                    && LoginPage.USERNAME.resolveAllFor(actor).get(0).isVisible());
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }
}
