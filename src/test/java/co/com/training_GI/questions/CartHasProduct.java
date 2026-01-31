package co.com.training_GI.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

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
        Target item = Target.the("cart item " + productName)
                .locatedBy("//div[contains(@class,'cart_item')]" +
                        "//div[contains(@class,'inventory_item_name') and normalize-space()='{0}']")
                .of(productName);
        return !item.resolveAllFor(actor).isEmpty();
    }
}
