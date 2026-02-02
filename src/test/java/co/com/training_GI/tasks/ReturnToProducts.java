package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductDetailsPage;
import co.com.training_GI.ui.ProductsPage;
import co.com.training_GI.interactions.ClickSafely;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import java.time.Duration;
import java.util.function.Supplier;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReturnToProducts {

    private ReturnToProducts() {
    }

    public static Task now() {
        return Tasks.instrumented(ReturnToProductsTask.class);
    }

    public static class ReturnToProductsTask implements Task {
        @Override
        public <T extends Actor> void performAs(T actor) {
            actor.attemptsTo(ClickSafely.on(ProductDetailsPage.BACK_TO_PRODUCTS));
            boolean onProducts = waitFor(actor, Duration.ofSeconds(10),
                    () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                            && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
            if (!onProducts) {
                actor.attemptsTo(Open.relativeUrl("/inventory.html"));
                boolean onProductsAfterFallback = waitFor(actor, Duration.ofSeconds(10),
                        () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                                && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
                if (!onProductsAfterFallback) {
                    throw new AssertionError("Products page did not load after returning");
                }
            }
        }

        private boolean waitFor(Actor actor, Duration timeout, Supplier<Boolean> condition) {
            WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), timeout);
            try {
                wait.until(driver -> condition.get());
                return true;
            } catch (TimeoutException ex) {
                return false;
            }
        }
    }
}
