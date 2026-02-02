package co.com.training_GI.support;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.serenitybdd.screenplay.Actor;

public final class NavigationGuard {

    private NavigationGuard() {
    }

    public static void ensure(Actor actor,
                              Duration initialTimeout,
                              Duration fallbackTimeout,
                              Supplier<Boolean> condition,
                              Consumer<Actor> fallback,
                              String errorMessage) {
        if (Waits.until(actor, initialTimeout, condition)) {
            return;
        }
        if (fallback != null) {
            fallback.accept(actor);
        }
        if (!Waits.until(actor, fallbackTimeout, condition)) {
            throw new AssertionError(errorMessage);
        }
    }
}
