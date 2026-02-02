package co.com.training_GI.tasks;

import co.com.training_GI.ui.CartPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.support.NavigationGuard;
import co.com.training_GI.support.Routes;
import co.com.training_GI.support.Timeouts;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

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
                        .forNoMoreThan(Timeouts.LONG),
                ClickSafely.on(ProductsPage.CART_ICON)
        );

        NavigationGuard.ensure(
                actor,
                Timeouts.MEDIUM,
                Timeouts.LONG,
                () -> !CartPage.CART_LIST.resolveAllFor(actor).isEmpty(),
                fallbackActor -> fallbackActor.attemptsTo(Open.relativeUrl(Routes.CART)),
                "Cart page did not load"
        );
    }
}
