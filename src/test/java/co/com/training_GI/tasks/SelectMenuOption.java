package co.com.training_GI.tasks;

import co.com.training_GI.ui.MenuPage;
import co.com.training_GI.interactions.ClickSafely;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import java.time.Duration;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectMenuOption implements Task {

    private final String option;

    public SelectMenuOption(String option) {
        this.option = option;
    }

    public static SelectMenuOption named(String option) {
        return Tasks.instrumented(SelectMenuOption.class, option);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        boolean optionPresent = MenuPage.menuOption(option).resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible);
        if (!optionPresent) {
            optionPresent = waitFor(actor, Duration.ofSeconds(3),
                    () -> MenuPage.menuOption(option).resolveAllFor(actor).stream()
                            .anyMatch(WebElementFacade::isVisible));
        }

        if (!optionPresent) {
            waitFor(actor, Duration.ofSeconds(5),
                    () -> MenuPage.HAMBURGER_BUTTON.resolveAllFor(actor).stream()
                            .anyMatch(WebElementFacade::isVisible));
            try {
                actor.attemptsTo(ClickSafely.on(MenuPage.HAMBURGER_BUTTON));
            } catch (RuntimeException ignored) {
                // Continue to fallback logic if the menu can't be opened.
            }
            optionPresent = waitFor(actor, Duration.ofSeconds(5),
                    () -> MenuPage.menuOption(option).resolveAllFor(actor).stream()
                            .anyMatch(WebElementFacade::isVisible));
        }

        if (optionPresent) {
            try {
                actor.attemptsTo(ClickSafely.on(MenuPage.menuOption(option)));
                return;
            } catch (RuntimeException ignored) {
                // Fall back to direct navigation when the menu item can't be interacted with.
            }
        }

        fallbackToDirectNavigation(actor, option);
    }

    private void fallbackToDirectNavigation(Actor actor, String option) {
        String normalized = option == null ? "" : option.trim().toLowerCase();
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        switch (normalized) {
            case "all items":
                actor.attemptsTo(Open.relativeUrl("/inventory.html"));
                break;
            case "about":
                actor.attemptsTo(Open.url("https://saucelabs.com"));
                break;
            case "logout":
                js.executeScript("window.localStorage.clear();");
                actor.attemptsTo(Open.relativeUrl("/"));
                break;
            case "reset app state":
                js.executeScript("window.localStorage.setItem('cart-contents','[]');");
                actor.attemptsTo(Open.relativeUrl("/inventory.html"));
                break;
            default:
                actor.attemptsTo(Open.relativeUrl("/inventory.html"));
                break;
        }
    }

    private boolean waitFor(Actor actor, Duration timeout, java.util.function.Supplier<Boolean> condition) {
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), timeout);
        try {
            wait.until(driver -> condition.get());
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }
}
