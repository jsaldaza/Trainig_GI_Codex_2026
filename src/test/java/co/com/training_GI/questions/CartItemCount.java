package co.com.training_GI.questions;

import co.com.training_GI.ui.CartPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CartItemCount implements Question<Integer> {

    public static CartItemCount value() {
        return new CartItemCount();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return CartPage.CART_ITEMS.resolveAllFor(actor).size();
    }
}
