package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductsPage;
import co.com.training_GI.support.MenuOption;
import co.com.training_GI.support.Timeouts;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;

public class ResetAppState {

    private ResetAppState() {
    }

    public static Task now() {
        return Task.where("{0} resets the app state",
                UseMenu.option(MenuOption.RESET_APP_STATE),
                WaitUntil.the(ProductsPage.CART_BADGE, isNotPresent())
                        .forNoMoreThan(Timeouts.LONG)
        );
    }
}
