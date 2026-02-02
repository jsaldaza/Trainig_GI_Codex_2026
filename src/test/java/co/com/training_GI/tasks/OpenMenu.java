package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.ui.MenuPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import java.time.Duration;
import java.util.function.Supplier;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class OpenMenu implements Task {

    public static OpenMenu now() {
        return Tasks.instrumented(OpenMenu.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                net.serenitybdd.screenplay.waits.WaitUntil.the(MenuPage.HAMBURGER_BUTTON, isClickable())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                ClickSafely.on(MenuPage.HAMBURGER_BUTTON)
        );

        boolean opened = waitFor(actor, Duration.ofSeconds(4),
                () -> !MenuPage.menuOption("All Items").resolveAllFor(actor).isEmpty());
        if (!opened) {
            JavaScriptClick.on(MenuPage.HAMBURGER_BUTTON).performAs(actor);
            waitFor(actor, Duration.ofSeconds(6),
                    () -> !MenuPage.menuOption("All Items").resolveAllFor(actor).isEmpty());
        }
    }

    private boolean waitFor(Actor actor, Duration timeout, Supplier<Boolean> condition) {
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), timeout);
        try {
            wait.until(driver -> condition.get());
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }
}
