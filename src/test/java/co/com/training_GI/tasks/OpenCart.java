package co.com.training_GI.tasks;

import co.com.training_GI.ui.CartPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import co.com.training_GI.interactions.ClickSafely;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

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

        try {
            actor.attemptsTo(
                    WaitUntil.the(CartPage.CART_LIST, isVisible())
                            .forNoMoreThan(Duration.ofSeconds(5))
            );
        } catch (RuntimeException ex) {
            actor.attemptsTo(
                    Open.relativeUrl("/cart.html"),
                    WaitUntil.the(CartPage.CART_LIST, isVisible())
                            .forNoMoreThan(Duration.ofSeconds(10))
            );
        }
    }
}
