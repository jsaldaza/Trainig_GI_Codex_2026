package co.com.training_GI.tasks;

import co.com.training_GI.ui.CartPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class RemoveProductFromCart {

    private RemoveProductFromCart() {
    }

    public static Task named(String productName) {
        return Task.where("{0} removes the product from the cart",
                WaitUntil.the(CartPage.removeButtonFor(productName), isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                Click.on(CartPage.removeButtonFor(productName)),
                WaitUntil.the(CartPage.removeButtonFor(productName), isNotPresent())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
