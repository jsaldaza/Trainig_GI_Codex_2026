package co.com.training_GI.questions;

import co.com.training_GI.support.CartState;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CartItemCount implements Question<Integer> {

    public static CartItemCount value() {
        return new CartItemCount();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return CartState.itemCount(actor);
    }
}
