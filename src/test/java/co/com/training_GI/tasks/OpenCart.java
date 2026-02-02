package co.com.training_GI.tasks;

import co.com.training_GI.ui.CartPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import co.com.training_GI.interactions.ClickSafely;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;
import java.util.function.Supplier;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class OpenCart implements Task {

    public static Task now() {
        return Tasks.instrumented(OpenCart.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.to(ProductsPage.CART_ICON),
                WaitUntil.the(ProductsPage.CART_ICON, isClickable())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                ClickSafely.on(ProductsPage.CART_ICON)
        );

        boolean cartVisible = waitFor(actor, Duration.ofSeconds(5),
                () -> !CartPage.CART_LIST.resolveAllFor(actor).isEmpty());
        if (!cartVisible) {
            actor.attemptsTo(
                    Open.relativeUrl("/cart.html")
            );
            boolean cartVisibleAfterFallback = waitFor(actor, Duration.ofSeconds(10),
                    () -> !CartPage.CART_LIST.resolveAllFor(actor).isEmpty());
            if (!cartVisibleAfterFallback) {
                throw new AssertionError("Cart page did not load");
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
