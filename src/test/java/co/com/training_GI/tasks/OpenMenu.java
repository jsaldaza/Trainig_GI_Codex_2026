package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.support.Waits;
import co.com.training_GI.ui.MenuPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class OpenMenu implements Task {

    public static OpenMenu now() {
        return Tasks.instrumented(OpenMenu.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                net.serenitybdd.screenplay.waits.WaitUntil.the(MenuPage.HAMBURGER_BUTTON, isClickable())
                        .forNoMoreThan(Timeouts.LONG),
                ClickSafely.on(MenuPage.HAMBURGER_BUTTON)
        );

        boolean opened = Waits.until(actor, Timeouts.QUICK,
                () -> !MenuPage.menuOption("All Items").resolveAllFor(actor).isEmpty());
        if (!opened) {
            ClickSafely.on(MenuPage.HAMBURGER_BUTTON).performAs(actor);
            Waits.until(actor, Timeouts.MENU,
                    () -> !MenuPage.menuOption("All Items").resolveAllFor(actor).isEmpty());
        }
    }
}
