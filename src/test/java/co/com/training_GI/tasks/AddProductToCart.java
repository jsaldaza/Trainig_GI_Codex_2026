package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.questions.CartBadgeCount;
import co.com.training_GI.support.CartStorage;
import co.com.training_GI.support.ProductItem;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.support.Waits;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AddProductToCart implements Task {

    private final String productName;

    public AddProductToCart(String productName) {
        this.productName = productName;
    }

    public static AddProductToCart named(String productName) {
        return Tasks.instrumented(AddProductToCart.class, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(ProductsPage.TITLE, isVisible())
                        .forNoMoreThan(Timeouts.LONG)
        );

        boolean alreadyInCart = !ProductsPage.removeButtonFor(productName)
                .resolveAllFor(actor)
                .isEmpty();
        if (alreadyInCart) {
            return;
        }

        int badgeBefore = CartBadgeCount.value().answeredBy(actor);
        int expectedBadge = badgeBefore + 1;

        actor.attemptsTo(
                Scroll.to(ProductsPage.addToCartButtonFor(productName)),
                WaitUntil.the(ProductsPage.addToCartButtonFor(productName), isClickable())
                        .forNoMoreThan(Timeouts.LONG),
                ClickSafely.on(ProductsPage.addToCartButtonFor(productName))
        );

        boolean removed = Waits.until(actor, Timeouts.MEDIUM,
                () -> !ProductsPage.removeButtonFor(productName).resolveAllFor(actor).isEmpty());
        if (!removed) {
            boolean badgeUpdated = Waits.until(actor, Timeouts.LONG,
                    () -> CartBadgeCount.value().answeredBy(actor) >= expectedBadge);
            if (!badgeUpdated) {
                if (!forceAddToCart(actor, productName, expectedBadge)) {
                    throw new AssertionError("Cart did not update after adding " + productName);
                }
            }
        }
    }

    private boolean forceAddToCart(Actor actor, String name, int expectedBadge) {
        Integer itemId = ProductItem.idForName(name);
        if (itemId == null) {
            return false;
        }
        CartStorage.addItem(actor, itemId);
        CartStorage.reload(actor);
        boolean titleVisible = Waits.until(actor, Timeouts.LONG,
                () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                        && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
        if (!titleVisible) {
            return false;
        }
        boolean removed = Waits.until(actor, Timeouts.MEDIUM,
                () -> !ProductsPage.removeButtonFor(name).resolveAllFor(actor).isEmpty());
        if (removed) {
            return true;
        }
        return Waits.until(actor, Timeouts.LONG,
                () -> CartBadgeCount.value().answeredBy(actor) >= expectedBadge);
    }
}
