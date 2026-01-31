package co.com.training_GI.stepdefinitions;

import co.com.training_GI.questions.LoginErrorMessage;
import co.com.training_GI.questions.LoginPageVisible;
import co.com.training_GI.support.Credentials;
import co.com.training_GI.tasks.EnsureLoggedIn;
import co.com.training_GI.tasks.Login;
import co.com.training_GI.ui.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class LoginStepDefinitions {

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

    @Then("the user should see the login page")
    public void theUserShouldSeeTheLoginPage() {
        theActorInTheSpotlight().should(seeThat(LoginPageVisible.isDisplayed(), is(true)));
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
