package co.com.training_GI.questions;

import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ProductsPageVisible implements Question<Boolean> {

    public static ProductsPageVisible isDisplayed() {
        return new ProductsPageVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return ProductsPage.TITLE.resolveFor(actor).isDisplayed();
    }
}
