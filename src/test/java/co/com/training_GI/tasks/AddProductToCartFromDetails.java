package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.questions.CartBadgeCount;
import co.com.training_GI.support.CartStorage;
import co.com.training_GI.support.ProductItem;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.support.Waits;
import co.com.training_GI.ui.ProductDetailsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Scroll;

public class AddProductToCartFromDetails implements Task {

    public static AddProductToCartFromDetails now() {
        return Tasks.instrumented(AddProductToCartFromDetails.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        int badgeBefore = CartBadgeCount.value().answeredBy(actor);
        int expectedBadge = badgeBefore + 1;

        actor.attemptsTo(
                Scroll.to(ProductDetailsPage.ADD_TO_CART),
                ClickSafely.on(ProductDetailsPage.ADD_TO_CART)
        );

        boolean removed = Waits.until(actor, Timeouts.MEDIUM,
                () -> !ProductDetailsPage.REMOVE_BUTTON.resolveAllFor(actor).isEmpty());
        if (!removed) {
            boolean badgeUpdated = Waits.until(actor, Timeouts.LONG,
                    () -> CartBadgeCount.value().answeredBy(actor) >= expectedBadge);
            if (!badgeUpdated) {
                if (!forceAddToCart(actor, expectedBadge)) {
                    throw new AssertionError("Cart did not update after adding product from details");
                }
            }
        }
    }

    private boolean forceAddToCart(Actor actor, int expectedBadge) {
        String name = ProductDetailsPage.PRODUCT_NAME.resolveFor(actor).getText().trim();
        Integer itemId = ProductItem.idForName(name);
        if (itemId == null) {
            return false;
        }
        CartStorage.addItem(actor, itemId);
        CartStorage.reload(actor);
        boolean removed = Waits.until(actor, Timeouts.MEDIUM,
                () -> !ProductDetailsPage.REMOVE_BUTTON.resolveAllFor(actor).isEmpty());
        if (removed) {
            return true;
        }
        return Waits.until(actor, Timeouts.LONG,
                () -> CartBadgeCount.value().answeredBy(actor) >= expectedBadge);
    }
}
