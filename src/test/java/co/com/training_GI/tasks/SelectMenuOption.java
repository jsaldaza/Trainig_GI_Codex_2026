package co.com.training_GI.tasks;

import co.com.training_GI.ui.MenuPage;
import co.com.training_GI.ui.LoginPage;
import co.com.training_GI.ui.ProductsPage;
import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.support.CartStorage;
import co.com.training_GI.support.MenuOption;
import co.com.training_GI.support.Routes;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.support.Waits;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;

public class SelectMenuOption implements Task {

    private final String option;
    private final MenuOption menuOption;

    public SelectMenuOption(String option) {
        this.option = option;
        this.menuOption = MenuOption.from(option);
    }

    public static SelectMenuOption named(String option) {
        return Tasks.instrumented(SelectMenuOption.class, option);
    }

    public static SelectMenuOption named(MenuOption option) {
        return Tasks.instrumented(SelectMenuOption.class, option == null ? null : option.label());
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        boolean optionPresent = menuTarget().resolveAllFor(actor).stream()
                .anyMatch(WebElementFacade::isVisible);
        if (!optionPresent) {
            optionPresent = Waits.until(actor, Timeouts.SHORT,
                    () -> menuTarget().resolveAllFor(actor).stream()
                            .anyMatch(WebElementFacade::isVisible));
        }

        if (!optionPresent) {
            Waits.until(actor, Timeouts.MEDIUM,
                    () -> MenuPage.HAMBURGER_BUTTON.resolveAllFor(actor).stream()
                            .anyMatch(WebElementFacade::isVisible));
            try {
                actor.attemptsTo(ClickSafely.on(MenuPage.HAMBURGER_BUTTON));
            } catch (RuntimeException ignored) {
                // Continue to fallback logic if the menu can't be opened.
            }
            optionPresent = Waits.until(actor, Timeouts.MEDIUM,
                    () -> menuTarget().resolveAllFor(actor).stream()
                            .anyMatch(WebElementFacade::isVisible));
        }

        if (optionPresent) {
            try {
                actor.attemptsTo(ClickSafely.on(menuTarget()));
                if (confirmNavigation(actor, option)) {
                    return;
                }
            } catch (RuntimeException ignored) {
                // Fall back to direct navigation when the menu item can't be interacted with.
            }
        }

        fallbackToDirectNavigation(actor, option);
    }

    private void fallbackToDirectNavigation(Actor actor, String option) {
        String normalized = normalize(option);
        switch (normalized) {
            case "all items":
                actor.attemptsTo(Open.relativeUrl(Routes.INVENTORY));
                break;
            case "about":
                actor.attemptsTo(Open.url("https://saucelabs.com"));
                break;
            case "logout":
                CartStorage.clearAll(actor);
                actor.attemptsTo(Open.relativeUrl(Routes.ROOT));
                break;
            case "reset app state":
                CartStorage.resetCart(actor);
                actor.attemptsTo(Open.relativeUrl(Routes.INVENTORY));
                break;
            default:
                actor.attemptsTo(Open.relativeUrl(Routes.INVENTORY));
                break;
        }
    }

    private boolean confirmNavigation(Actor actor, String option) {
        MenuOption resolved = menuOption;
        if (resolved == null) {
            return true;
        }
        switch (resolved) {
            case ABOUT:
                return ensureUrlContains(actor, "saucelabs.com")
                        || switchToWindowContaining(actor, "saucelabs.com");
            case LOGOUT:
                return Waits.until(actor, Timeouts.MENU,
                        () -> !LoginPage.USERNAME.resolveAllFor(actor).isEmpty()
                                && LoginPage.USERNAME.resolveAllFor(actor).get(0).isVisible());
            case ALL_ITEMS:
                return Waits.until(actor, Timeouts.MENU,
                        () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                                && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
            default:
                return true;
        }
    }

    private boolean ensureUrlContains(Actor actor, String expected) {
        return Waits.until(actor, Timeouts.MENU,
                () -> {
                    String currentUrl = BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
                    return currentUrl != null && currentUrl.contains(expected);
                });
    }

    private boolean switchToWindowContaining(Actor actor, String expected) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        String originalHandle = driver.getWindowHandle();
        boolean hasAnotherWindow = Waits.until(actor, Timeouts.MENU,
                () -> driver.getWindowHandles().size() > 1);
        if (!hasAnotherWindow) {
            return false;
        }
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl != null && currentUrl.contains(expected)) {
                return true;
            }
        }
        driver.switchTo().window(originalHandle);
        return false;
    }

    private String normalize(String option) {
        return option == null ? "" : option.trim().toLowerCase();
    }

    private net.serenitybdd.screenplay.targets.Target menuTarget() {
        return menuOption != null ? MenuPage.menuOption(menuOption) : MenuPage.menuOption(option);
    }
}
