package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;

public class ResetAppState {

    private ResetAppState() {
    }

    public static Task now() {
        return Task.where("{0} resets the app state",
                OpenMenu.now(),
                SelectMenuOption.named("Reset App State"),
                WaitUntil.the(ProductsPage.CART_BADGE, isNotPresent())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
