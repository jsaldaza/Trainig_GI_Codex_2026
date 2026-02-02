package co.com.training_GI.tasks;

import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.support.NavigationGuard;
import co.com.training_GI.support.ProductItem;
import co.com.training_GI.support.Routes;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.ui.ProductDetailsPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;

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
                ClickSafely.on(ProductsPage.productLinkFor(productName))
        );

        NavigationGuard.ensure(
                actor,
                Timeouts.MEDIUM,
                Timeouts.LONG,
                () -> !ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).isEmpty()
                        && ProductDetailsPage.PRODUCT_NAME.resolveAllFor(actor).get(0).isVisible(),
                fallbackActor -> {
                    Integer id = ProductItem.idForName(productName);
                    if (id != null) {
                        fallbackActor.attemptsTo(Open.relativeUrl(Routes.inventoryItem(id)));
                    }
                },
                "Product details did not open for " + productName
        );
    }
}
