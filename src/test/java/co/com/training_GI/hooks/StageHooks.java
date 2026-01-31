package co.com.training_GI.hooks;

import io.cucumber.java.Before;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class StageHooks {

    @Managed
    WebDriver driver;

    @Before(order = 1)
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        theActorCalled("User").can(BrowseTheWeb.with(driver));
    }
}
