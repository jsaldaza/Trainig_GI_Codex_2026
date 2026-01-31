package co.com.training_GI.tasks;

import co.com.training_GI.ui.CartPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Task;
import co.com.training_GI.interactions.ClickSafely;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class OpenCart {

    private OpenCart() {
    }

    public static Task now() {
        return Task.where("{0} opens the cart",
                Scroll.to(ProductsPage.CART_ICON),
                WaitUntil.the(ProductsPage.CART_ICON, isClickable())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                ClickSafely.on(ProductsPage.CART_ICON),
                Open.relativeUrl("/cart.html"),
                WaitUntil.the(CartPage.CART_LIST, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
