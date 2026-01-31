package co.com.training_GI.questions;

import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import java.util.List;

public class CartBadgeCount implements Question<Integer> {

    public static CartBadgeCount value() {
        return new CartBadgeCount();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        List<WebElementFacade> badges = ProductsPage.CART_BADGE.resolveAllFor(actor);
        if (badges.isEmpty()) {
            return 0;
        }
        String text = badges.get(0).getText().trim();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
