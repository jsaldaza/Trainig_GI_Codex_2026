package co.com.training_GI.stepdefinitions;

import co.com.training_GI.support.MenuOption;
import co.com.training_GI.tasks.OpenMenu;
import co.com.training_GI.tasks.ResetAppState;
import co.com.training_GI.tasks.SelectMenuOption;
import io.cucumber.java.en.When;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class MenuStepDefinitions {

    @When("the user opens the menu")
    public void theUserOpensTheMenu() {
        theActorInTheSpotlight().attemptsTo(OpenMenu.now());
    }

    @When("the user selects the menu option {string}")
    public void theUserSelectsTheMenuOption(String option) {
        MenuOption menuOption = MenuOption.from(option);
        if (menuOption != null) {
            theActorInTheSpotlight().attemptsTo(SelectMenuOption.named(menuOption));
        } else {
            theActorInTheSpotlight().attemptsTo(SelectMenuOption.named(option));
        }
    }

    @When("the user resets the app state")
    public void theUserResetsTheAppState() {
        theActorInTheSpotlight().attemptsTo(ResetAppState.now());
    }
}
