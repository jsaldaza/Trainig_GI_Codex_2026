package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductDetailsPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AddProductToCartFromDetails {

    private AddProductToCartFromDetails() {
    }

    public static Task now() {
        return Task.where("{0} adds the product to the cart from details",
                Click.on(ProductDetailsPage.ADD_TO_CART),
                WaitUntil.the(ProductDetailsPage.REMOVE_BUTTON, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
