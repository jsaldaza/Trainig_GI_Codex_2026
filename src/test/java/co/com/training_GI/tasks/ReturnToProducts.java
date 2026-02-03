package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductDetailsPage;
import co.com.training_GI.ui.ProductsPage;
import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.support.AppConfig;
import co.com.training_GI.support.Routes;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.support.Waits;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class ReturnToProducts {

    private ReturnToProducts() {
    }

    public static Task now() {
        return Tasks.instrumented(ReturnToProductsTask.class);
    }

    public static class ReturnToProductsTask implements Task {
        @Override
        public <T extends Actor> void performAs(T actor) {
            actor.attemptsTo(ClickSafely.on(ProductDetailsPage.BACK_TO_PRODUCTS));
            boolean onProducts = Waits.until(actor, Timeouts.LONG,
                    () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                            && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
            if (!onProducts) {
                actor.attemptsTo(ClickSafely.on(ProductDetailsPage.BACK_TO_PRODUCTS));
                onProducts = Waits.until(actor, Timeouts.MEDIUM,
                        () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                                && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
            }
            if (!onProducts && AppConfig.navigationFallback()) {
                actor.attemptsTo(Open.relativeUrl(Routes.INVENTORY));
                onProducts = Waits.until(actor, Timeouts.LONG,
                        () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                                && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible());
            }
            if (!onProducts) {
                throw new AssertionError("Products page did not load after returning");
            }
        }
    }
}
