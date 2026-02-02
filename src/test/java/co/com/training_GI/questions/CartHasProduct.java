package co.com.training_GI.questions;

import co.com.training_GI.support.CartState;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CartHasProduct implements Question<Boolean> {

    private final String productName;

    private CartHasProduct(String productName) {
        this.productName = productName;
    }

    public static CartHasProduct named(String productName) {
        return new CartHasProduct(productName);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return CartState.hasProduct(actor, productName);
    }
}
