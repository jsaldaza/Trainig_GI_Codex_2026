package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.support.AppConfig;
import co.com.training_GI.support.ProductItem;
import co.com.training_GI.support.Routes;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.support.Waits;
import co.com.training_GI.ui.ProductDetailsPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class OpenProductDetails implements Task {

    private final String productName;

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
                WaitUntil.the(ProductsPage.productLinkFor(productName), isClickable())
                        .forNoMoreThan(Timeouts.LONG),
                ClickSafely.on(ProductsPage.productLinkFor(productName))
        );

        boolean opened = Waits.until(actor, Timeouts.MEDIUM,
                () -> !ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).isEmpty()
                        && ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).get(0).isVisible());
        if (!opened) {
            actor.attemptsTo(ClickSafely.on(ProductsPage.productLinkFor(productName)));
            opened = Waits.until(actor, Timeouts.MEDIUM,
                    () -> !ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).isEmpty()
                            && ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).get(0).isVisible());
        }
        if (!opened && AppConfig.navigationFallback()) {
            Integer id = ProductItem.idForName(productName);
            if (id != null) {
                actor.attemptsTo(Open.relativeUrl(Routes.inventoryItem(id)));
            }
            opened = Waits.until(actor, Timeouts.LONG,
                    () -> !ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).isEmpty()
                            && ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).get(0).isVisible());
        }
        if (!opened) {
            throw new AssertionError("Product details did not open for " + productName);
        }
    }
}
