package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductDetailsPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ReturnToProducts {

    private ReturnToProducts() {
    }

    public static Task now() {
        return Task.where("{0} returns to products",
                Click.on(ProductDetailsPage.BACK_TO_PRODUCTS),
                WaitUntil.the(ProductsPage.TITLE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
