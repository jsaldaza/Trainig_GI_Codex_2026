package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductDetailsPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class OpenProductDetails {

    private OpenProductDetails() {
    }

    public static Task named(String productName) {
        return Task.where("{0} opens the product details",
                Click.on(ProductsPage.productLinkFor(productName)),
                WaitUntil.the(ProductDetailsPage.PRODUCT_NAME, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
