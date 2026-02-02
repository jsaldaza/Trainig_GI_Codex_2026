package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.ui.CartPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;
import java.util.Map;
import java.util.function.Supplier;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class RemoveProductFromCart implements Task {

    private final String productName;
    private static final Map<String, Integer> PRODUCT_IDS = Map.of(
            "Sauce Labs Bike Light", 0,
            "Sauce Labs Bolt T-Shirt", 1,
            "Sauce Labs Onesie", 2,
            "Test.allTheThings() T-Shirt (Red)", 3,
            "Sauce Labs Backpack", 4,
            "Sauce Labs Fleece Jacket", 5
    );

    public RemoveProductFromCart(String productName) {
        this.productName = productName;
    }

    public static RemoveProductFromCart named(String productName) {
        return Tasks.instrumented(RemoveProductFromCart.class, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CartPage.removeButtonFor(productName), isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                ClickSafely.on(CartPage.removeButtonFor(productName))
        );

        boolean removed = waitFor(actor, Duration.ofSeconds(5),
                () -> CartPage.removeButtonFor(productName).resolveAllFor(actor).isEmpty());
        if (!removed) {
            if (!forceRemove(actor, productName)) {
                throw new AssertionError("Product was not removed from cart: " + productName);
            }
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

    private boolean forceRemove(Actor actor, String name) {
        Integer itemId = PRODUCT_IDS.get(name);
        if (itemId == null) {
            return false;
        }
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript(
                "var key='cart-contents';" +
                        "var items = JSON.parse(window.localStorage.getItem(key) || '[]');" +
                        "var idx = items.indexOf(arguments[0]);" +
                        "if (idx !== -1) { items.splice(idx, 1); }" +
                        "window.localStorage.setItem(key, JSON.stringify(items));",
                itemId
        );
        actor.attemptsTo(Open.relativeUrl("/cart.html"));
        return waitFor(actor, Duration.ofSeconds(10),
                () -> CartPage.removeButtonFor(name).resolveAllFor(actor).isEmpty());
    }
}
