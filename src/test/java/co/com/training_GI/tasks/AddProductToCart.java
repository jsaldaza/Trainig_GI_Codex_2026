package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AddProductToCart {

    private AddProductToCart() {
    }

    public static Task named(String productName) {
        return Task.where("{0} adds the product to the cart",
                WaitUntil.the(ProductsPage.TITLE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                WaitUntil.the(ProductsPage.addToCartButtonFor(productName), isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                JavaScriptClick.on(ProductsPage.addToCartButtonFor(productName)),
                WaitUntil.the(ProductsPage.removeButtonFor(productName), isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
