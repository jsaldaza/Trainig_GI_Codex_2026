package co.com.training_GI.stepdefinitions;

import co.com.training_GI.questions.CartBadgeCount;
import co.com.training_GI.questions.CartHasProduct;
import co.com.training_GI.questions.CartIsEmpty;
import co.com.training_GI.questions.CartItemCount;
import co.com.training_GI.questions.CurrentUrlContains;
import co.com.training_GI.questions.LoginErrorMessage;
import co.com.training_GI.questions.LoginPageVisible;
import co.com.training_GI.questions.ProductsPageVisible;
import co.com.training_GI.support.Credentials;
import co.com.training_GI.tasks.AddProductToCart;
import co.com.training_GI.tasks.AddProductToCartFromDetails;
import co.com.training_GI.tasks.ContinueShopping;
import co.com.training_GI.tasks.EnsureLoggedIn;
import co.com.training_GI.tasks.Login;
import co.com.training_GI.tasks.OpenMenu;
import co.com.training_GI.tasks.OpenCart;
import co.com.training_GI.tasks.OpenProductDetails;
import co.com.training_GI.tasks.RemoveProductFromCart;
import co.com.training_GI.tasks.ResetAppState;
import co.com.training_GI.tasks.ReturnToProducts;
import co.com.training_GI.tasks.SelectMenuOption;
import co.com.training_GI.ui.LoginPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class LoginStepDefinitions {

    @Managed
    WebDriver driver;

    @Before(order = 1)
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        theActorCalled("User").can(BrowseTheWeb.with(driver));
    }

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        theActorInTheSpotlight().wasAbleTo(Open.browserOn().the(LoginPage.class));
    }

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        theActorInTheSpotlight().attemptsTo(EnsureLoggedIn.withDefaultCredentials());
    }

    @When("the user logs in with username {string} and password {string}")
    public void theUserLogsInWithUsernameAndPassword(String username, String password) {
        theActorInTheSpotlight().attemptsTo(Login.withCredentials(username, password));
    }

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() {
        theActorInTheSpotlight().attemptsTo(Login.withCredentials(
                Credentials.username(),
                Credentials.password()
        ));
    }

    @When("the user resets the app state")
    public void theUserResetsTheAppState() {
        theActorInTheSpotlight().attemptsTo(ResetAppState.now());
    }

    @When("the user adds the product {string} to the cart")
    public void theUserAddsTheProductToTheCart(String productName) {
        theActorInTheSpotlight().attemptsTo(AddProductToCart.named(productName));
    }

    @When("the user opens the cart")
    public void theUserOpensTheCart() {
        theActorInTheSpotlight().attemptsTo(OpenCart.now());
    }

    @When("the user removes the product {string} from the cart")
    public void theUserRemovesTheProductFromTheCart(String productName) {
        theActorInTheSpotlight().attemptsTo(RemoveProductFromCart.named(productName));
    }

    @When("the user opens product details for {string}")
    public void theUserOpensProductDetailsFor(String productName) {
        theActorInTheSpotlight().attemptsTo(OpenProductDetails.named(productName));
    }

    @When("the user adds the product to the cart from details")
    public void theUserAddsTheProductToTheCartFromDetails() {
        theActorInTheSpotlight().attemptsTo(AddProductToCartFromDetails.now());
    }

    @When("the user returns to products")
    public void theUserReturnsToProducts() {
        theActorInTheSpotlight().attemptsTo(ReturnToProducts.now());
    }

    @When("the user continues shopping")
    public void theUserContinuesShopping() {
        theActorInTheSpotlight().attemptsTo(ContinueShopping.now());
    }

    @When("the user opens the menu")
    public void theUserOpensTheMenu() {
        theActorInTheSpotlight().attemptsTo(OpenMenu.now());
    }

    @When("the user selects the menu option {string}")
    public void theUserSelectsTheMenuOption(String option) {
        theActorInTheSpotlight().attemptsTo(SelectMenuOption.named(option));
    }

    @Then("the user should see the products page")
    public void theUserShouldSeeTheProductsPage() {
        theActorInTheSpotlight().should(seeThat(ProductsPageVisible.isDisplayed(), is(true)));
    }

    @Then("the cart should contain the product {string}")
    public void theCartShouldContainTheProduct(String productName) {
        theActorInTheSpotlight().should(seeThat(CartHasProduct.named(productName), is(true)));
    }

    @Then("the cart badge should show {int}")
    public void theCartBadgeShouldShow(int expected) {
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

    @Then("the user should see the login page")
    public void theUserShouldSeeTheLoginPage() {
        theActorInTheSpotlight().should(seeThat(LoginPageVisible.isDisplayed(), is(true)));
    }

    @Then("the current url should contain {string}")
    public void theCurrentUrlShouldContain(String expected) {
        theActorInTheSpotlight().should(seeThat(CurrentUrlContains.text(expected), is(true)));
    }

    @Then("a login error message should be displayed")
    public void aLoginErrorMessageShouldBeDisplayed() {
        theActorInTheSpotlight().should(seeThat(LoginErrorMessage.text(), not(emptyOrNullString())));
    }

    @Then("the login error message should contain {string}")
    public void theLoginErrorMessageShouldContain(String expected) {
        theActorInTheSpotlight().should(seeThat(LoginErrorMessage.text(), containsString(expected)));
    }
}
