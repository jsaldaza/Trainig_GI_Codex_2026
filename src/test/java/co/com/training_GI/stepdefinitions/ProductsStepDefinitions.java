package co.com.training_GI.stepdefinitions;

import co.com.training_GI.questions.ProductsPageVisible;
import co.com.training_GI.support.ProductItem;
import co.com.training_GI.tasks.AddProductToCart;
import co.com.training_GI.tasks.AddProductToCartFromDetails;
import co.com.training_GI.tasks.OpenProductDetails;
import co.com.training_GI.tasks.ReturnToProducts;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.is;

public class ProductsStepDefinitions {

    @When("the user adds the product {string} to the cart")
    public void theUserAddsTheProductToTheCart(String productName) {
        theActorInTheSpotlight().attemptsTo(AddProductToCart.named(canonicalProductName(productName)));
    }

    @When("the user opens product details for {string}")
    public void theUserOpensProductDetailsFor(String productName) {
        theActorInTheSpotlight().attemptsTo(OpenProductDetails.named(canonicalProductName(productName)));
    }

    @When("the user adds the product to the cart from details")
    public void theUserAddsTheProductToTheCartFromDetails() {
        theActorInTheSpotlight().attemptsTo(AddProductToCartFromDetails.now());
    }

    @When("the user returns to products")
    public void theUserReturnsToProducts() {
        theActorInTheSpotlight().attemptsTo(ReturnToProducts.now());
    }

    @Then("the user should see the products page")
    public void theUserShouldSeeTheProductsPage() {
        theActorInTheSpotlight().should(seeThat(ProductsPageVisible.isDisplayed(), is(true)));
    }

    private String canonicalProductName(String productName) {
        ProductItem item = ProductItem.fromName(productName);
        return item == null ? productName : item.label();
    }
}
