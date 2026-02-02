package co.com.training_GI.tasks;

import co.com.training_GI.ui.ProductDetailsPage;
import co.com.training_GI.ui.ProductsPage;
import co.com.training_GI.interactions.ClickSafely;
import co.com.training_GI.support.NavigationGuard;
import co.com.training_GI.support.Routes;
import co.com.training_GI.support.Timeouts;
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
            NavigationGuard.ensure(
                    actor,
                    Timeouts.LONG,
                    Timeouts.LONG,
                    () -> !ProductsPage.TITLE.resolveAllFor(actor).isEmpty()
                            && ProductsPage.TITLE.resolveAllFor(actor).get(0).isVisible(),
                    fallbackActor -> fallbackActor.attemptsTo(Open.relativeUrl(Routes.INVENTORY)),
                    "Products page did not load after returning"
            );
        }
    }
}
