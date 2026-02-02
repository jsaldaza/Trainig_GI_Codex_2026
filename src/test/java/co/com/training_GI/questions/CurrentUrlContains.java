package co.com.training_GI.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import java.time.Duration;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CurrentUrlContains implements Question<Boolean> {

    private final String expected;

    private CurrentUrlContains(String expected) {
        this.expected = expected;
    }

    public static CurrentUrlContains text(String expected) {
        return new CurrentUrlContains(expected);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(10));
        try {
            wait.until(driver -> {
                String currentUrl = driver.getCurrentUrl();
                return currentUrl != null && currentUrl.contains(expected);
            });
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }
}
