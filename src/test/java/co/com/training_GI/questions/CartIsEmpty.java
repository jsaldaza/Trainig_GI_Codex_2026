package co.com.training_GI.questions;

import co.com.training_GI.ui.CartPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CartIsEmpty implements Question<Boolean> {

    public static CartIsEmpty value() {
        return new CartIsEmpty();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return CartPage.CART_ITEMS.resolveAllFor(actor).isEmpty();
    }
}
