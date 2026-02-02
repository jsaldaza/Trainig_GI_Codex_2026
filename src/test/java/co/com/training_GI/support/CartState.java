package co.com.training_GI.support;

import co.com.training_GI.ui.CartPage;
import net.serenitybdd.screenplay.Actor;

public final class CartState {

    private CartState() {
    }

    public static int itemCount(Actor actor) {
        return CartPage.CART_ITEMS.resolveAllFor(actor).size();
    }

    public static boolean isEmpty(Actor actor) {
        return itemCount(actor) == 0;
    }

    public static boolean hasProduct(Actor actor, String productName) {
        return !CartPage.cartItemName(productName).resolveAllFor(actor).isEmpty();
    }
}
