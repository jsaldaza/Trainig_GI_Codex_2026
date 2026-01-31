package co.com.training_GI.tasks;

import co.com.training_GI.ui.MenuPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class OpenMenu {

    private OpenMenu() {
    }

    public static Task now() {
        return Task.where("{0} opens the hamburger menu",
                WaitUntil.the(MenuPage.HAMBURGER_BUTTON, isClickable())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                JavaScriptClick.on(MenuPage.HAMBURGER_BUTTON),
                WaitUntil.the(MenuPage.menuOption("All Items"), isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
