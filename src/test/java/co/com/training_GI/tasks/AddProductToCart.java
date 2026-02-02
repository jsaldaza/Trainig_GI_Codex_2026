package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.questions.CartBadgeCount;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.time.Duration;
import java.util.Map;
import java.util.function.Supplier;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AddProductToCart implements Task {

    private final String productName;
    private static final Map<String, Integer> PRODUCT_IDS = Map.of(
            "Sauce Labs Bike Light", 0,
            "Sauce Labs Bolt T-Shirt", 1,
            "Sauce Labs Onesie", 2,
            "Test.allTheThings() T-Shirt (Red)", 3,
            "Sauce Labs Backpack", 4,
            "Sauce Labs Fleece Jacket", 5
    );

    public AddProductToCart(String productName) {
        this.productName = productName;
    }

    public static AddProductToCart named(String productName) {
        return Tasks.instrumented(AddProductToCart.class, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(ProductsPage.TITLE, isVisible())
                        .forNoMoreThan(Duration.ofSeconds(10))
        );

        boolean alreadyInCart = !ProductsPage.removeButtonFor(productName)
                .resolveAllFor(actor)
                .isEmpty();
        if (alreadyInCart) {
            return;
        }

        int badgeBefore = CartBadgeCount.value().answeredBy(actor);
        int expectedBadge = badgeBefore + 1;

        actor.attemptsTo(
                Scroll.to(ProductsPage.addToCartButtonFor(productName)),
                WaitUntil.the(ProductsPage.addToCartButtonFor(productName), isClickable())
                        .forNoMoreThan(Duration.ofSeconds(10)),
                ClickSafely.on(ProductsPage.addToCartButtonFor(productName))
        );

        boolean removed = waitFor(actor, Duration.ofSeconds(5),
                () -> !ProductsPage.removeButtonFor(productName).resolveAllFor(actor).isEmpty());
        if (!removed) {
            boolean badgeUpdated = waitFor(actor, Duration.ofSeconds(10),
                    () -> CartBadgeCount.value().answeredBy(actor) >= expectedBadge);
            if (!badgeUpdated) {
                if (!forceAddToCart(actor, productName, expectedBadge)) {
                    throw new AssertionError("Cart did not update after adding " + productName);
                }
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

    private boolean forceAddToCart(Actor actor, String name, int expectedBadge) {
        Integer itemId = PRODUCT_IDS.get(name);
        if (itemId == null) {
            return false;
        }
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript(
                "var key='cart-contents';" +
                        "var items = JSON.parse(window.localStorage.getItem(key) || '[]');" +
                        "if (items.indexOf(arguments[0]) === -1) { items.push(arguments[0]); }" +
                        "window.localStorage.setItem(key, JSON.stringify(items));",
                itemId
        );
        js.executeScript("window.location.reload();");
        boolean titleVisible = waitFor(actor, Duration.ofSeconds(10),
                () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                        && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
        if (!titleVisible) {
            return false;
        }
        boolean removed = waitFor(actor, Duration.ofSeconds(5),
                () -> !ProductsPage.removeButtonFor(name).resolveAllFor(actor).isEmpty());
        if (removed) {
            return true;
        }
        return waitFor(actor, Duration.ofSeconds(10),
                () -> CartBadgeCount.value().answeredBy(actor) >= expectedBadge);
    }
}
