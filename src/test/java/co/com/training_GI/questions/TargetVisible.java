package co.com.training_GI.questions;

import co.com.training_GI.support.Timeouts;
import co.com.training_GI.support.Waits;
import java.time.Duration;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class TargetVisible implements Question<Boolean> {

    private final Target target;
    private final Duration timeout;

    private TargetVisible(Target target, Duration timeout) {
        this.target = target;
        this.timeout = timeout;
    }

    public static TargetVisible of(Target target) {
        return new TargetVisible(target, Timeouts.LONG);
    }

    public static TargetVisible of(Target target, Duration timeout) {
        return new TargetVisible(target, timeout);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return Waits.until(actor, timeout, () -> {
            List<WebElementFacade> elements = target.resolveAllFor(actor);
            return !elements.isEmpty() && elements.get(0).isVisible();
        });
    }
}
