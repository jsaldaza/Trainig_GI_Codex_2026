package co.com.training_GI.questions;

import co.com.training_GI.support.Waits;
import co.com.training_GI.support.Timeouts;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class CurrentUrlContains implements Question<Boolean> {

    private final String expected;

    private CurrentUrlContains(String expected) {
        this.expected = expected;
    }

    public static CurrentUrlContains text(String expected) {
        return new CurrentUrlContains(expected);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return Waits.until(actor, Timeouts.LONG, () -> {
            String currentUrl = BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
            return currentUrl != null && currentUrl.contains(expected);
        });
    }
}
