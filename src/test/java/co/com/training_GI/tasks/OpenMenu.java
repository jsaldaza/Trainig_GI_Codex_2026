package co.com.training_GI.tasks;

import co.com.training_GI.ui.MenuPage;
import net.serenitybdd.screenplay.Task;
import co.com.training_GI.interactions.ClickSafely;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;

public class OpenMenu {

    private OpenMenu() {
    }

    public static Task now() {
        return Task.where("{0} opens the hamburger menu",
                WaitUntil.the(MenuPage.HAMBURGER_BUTTON, isClickable())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                ClickSafely.on(MenuPage.HAMBURGER_BUTTON),
                WaitUntil.the(MenuPage.menuOption("All Items"), isPresent())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );
    }
}
