package co.com.training_GI.stepdefinitions;

import co.com.training_GI.questions.CartBadgeCount;
import co.com.training_GI.questions.CartHasProduct;
import co.com.training_GI.questions.CartIsEmpty;
import co.com.training_GI.questions.CartItemCount;
import co.com.training_GI.support.ProductItem;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.tasks.ContinueShopping;
import co.com.training_GI.tasks.OpenCart;
import co.com.training_GI.tasks.RemoveProductFromCart;
import co.com.training_GI.ui.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.is;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;

public class CartStepDefinitions {

    @When("the user opens the cart")
    public void theUserOpensTheCart() {
        theActorInTheSpotlight().attemptsTo(OpenCart.now());
    }

    @When("the user removes the product {string} from the cart")
    public void theUserRemovesTheProductFromTheCart(String productName) {
        theActorInTheSpotlight().attemptsTo(RemoveProductFromCart.named(canonicalProductName(productName)));
    }

    @When("the user continues shopping")
    public void theUserContinuesShopping() {
        theActorInTheSpotlight().attemptsTo(ContinueShopping.now());
    }

    @Then("the cart should contain the product {string}")
    public void theCartShouldContainTheProduct(String productName) {
        theActorInTheSpotlight().should(seeThat(CartHasProduct.named(canonicalProductName(productName)), is(true)));
    }

    @Then("the cart badge should show {int}")
    public void theCartBadgeShouldShow(int expected) {
        if (expected > 0) {
            theActorInTheSpotlight().attemptsTo(
                    WaitUntil.the(ProductsPage.CART_BADGE, isVisible())
                            .forNoMoreThan(Timeouts.LONG)
            );
        } else {
            theActorInTheSpotlight().attemptsTo(
                    WaitUntil.the(ProductsPage.CART_BADGE, isNotPresent())
                            .forNoMoreThan(Timeouts.LONG)
            );
        }
        theActorInTheSpotlight().should(seeThat(CartBadgeCount.value(), is(expected)));
    }

    @Then("the cart should have {int} items")
    public void theCartShouldHaveItems(int expected) {
        theActorInTheSpotlight().should(seeThat(CartItemCount.value(), is(expected)));
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        theActorInTheSpotlight().should(seeThat(CartIsEmpty.value(), is(true)));
    }

    private String canonicalProductName(String productName) {
        ProductItem item = ProductItem.fromName(productName);
        return item == null ? productName : item.label();
    }
}
