package co.com.training_GI.tasks;

import co.com.training_GI.ui.CartPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ContinueShopping {

    private ContinueShopping() {
    }

    public static Task now() {
        return Task.where("{0} continues shopping",
                Click.on(CartPage.CONTINUE_SHOPPING),
                WaitUntil.the(ProductsPage.TITLE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
