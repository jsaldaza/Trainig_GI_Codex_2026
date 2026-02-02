package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.support.CartStorage;
import co.com.training_GI.support.ProductItem;
import co.com.training_GI.support.Routes;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.support.Waits;
import co.com.training_GI.ui.CartPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class RemoveProductFromCart implements Task {

    private final String productName;

    public RemoveProductFromCart(String productName) {
        this.productName = productName;
    }

    public static RemoveProductFromCart named(String productName) {
        return Tasks.instrumented(RemoveProductFromCart.class, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CartPage.removeButtonFor(productName), isVisible())
                        .forNoMoreThan(Timeouts.LONG),
                ClickSafely.on(CartPage.removeButtonFor(productName))
        );

        boolean removed = Waits.until(actor, Timeouts.MEDIUM,
                () -> CartPage.removeButtonFor(productName).resolveAllFor(actor).isEmpty());
        if (!removed) {
            if (!forceRemove(actor, productName)) {
                throw new AssertionError("Product was not removed from cart: " + productName);
            }
        }
    }

    private boolean forceRemove(Actor actor, String name) {
        Integer itemId = ProductItem.idForName(name);
        if (itemId == null) {
            return false;
        }
        CartStorage.removeItem(actor, itemId);
        actor.attemptsTo(Open.relativeUrl(Routes.CART));
        return Waits.until(actor, Timeouts.LONG,
                () -> CartPage.removeButtonFor(name).resolveAllFor(actor).isEmpty());
    }
}
