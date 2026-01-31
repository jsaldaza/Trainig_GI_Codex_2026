package co.com.training_GI.stepdefinitions;

import co.com.training_GI.questions.CurrentUrlContains;
import io.cucumber.java.en.Then;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.is;

public class CommonStepDefinitions {

    @Then("the current url should contain {string}")
    public void theCurrentUrlShouldContain(String expected) {
        theActorInTheSpotlight().should(seeThat(CurrentUrlContains.text(expected), is(true)));
    }
}
