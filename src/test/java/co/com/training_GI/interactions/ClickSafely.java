package co.com.training_GI.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.targets.Target;

public class ClickSafely implements Interaction {

    private final Target target;

    public ClickSafely(Target target) {
        this.target = target;
    }

    public static Interaction on(Target target) {
        return Tasks.instrumented(ClickSafely.class, target);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            Click.on(target).performAs(actor);
        } catch (RuntimeException ex) {
            JavaScriptClick.on(target).performAs(actor);
        }
    }
}
