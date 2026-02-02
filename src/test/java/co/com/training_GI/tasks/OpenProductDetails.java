package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.ui.ProductDetailsPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;
import java.time.Duration;
import java.util.Map;
import java.util.function.Supplier;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpenProductDetails implements Task {

    private final String productName;
    private static final Map<String, Integer> PRODUCT_IDS = Map.of(
            "Sauce Labs Bike Light", 0,
            "Sauce Labs Bolt T-Shirt", 1,
            "Sauce Labs Onesie", 2,
            "Test.allTheThings() T-Shirt (Red)", 3,
            "Sauce Labs Backpack", 4,
            "Sauce Labs Fleece Jacket", 5
    );

    public OpenProductDetails(String productName) {
        this.productName = productName;
    }

    public static OpenProductDetails named(String productName) {
        return Tasks.instrumented(OpenProductDetails.class, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.to(ProductsPage.productLinkFor(productName)),
                ClickSafely.on(ProductsPage.productLinkFor(productName))
        );

        boolean opened = waitFor(actor, Duration.ofSeconds(5),
                () -> !ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).isEmpty()
                        && ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).get(0).isVisible());
        if (!opened) {
            Integer id = PRODUCT_IDS.get(productName);
            if (id != null) {
                actor.attemptsTo(Open.relativeUrl("/inventory-item.html?id=" + id));
            }
            boolean openedAfterFallback = waitFor(actor, Duration.ofSeconds(10),
                    () -> !ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).isEmpty()
                            && ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).get(0).isVisible());
            if (!openedAfterFallback) {
                throw new AssertionError("Product details did not open for " + productName);
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
}
