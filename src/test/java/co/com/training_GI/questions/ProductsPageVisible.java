package co.com.training_GI.questions;

import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import java.time.Duration;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPageVisible implements Question<Boolean> {

    public static ProductsPageVisible isDisplayed() {
        return new ProductsPageVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(10));
        try {
            wait.until(driver -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                    && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }
}
