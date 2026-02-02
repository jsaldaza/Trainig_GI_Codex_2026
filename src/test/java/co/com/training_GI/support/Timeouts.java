package co.com.training_GI.support;

import java.time.Duration;

public final class Timeouts {

    public static final Duration SHORT = Duration.ofSeconds(3);
    public static final Duration QUICK = Duration.ofSeconds(4);
    public static final Duration MEDIUM = Duration.ofSeconds(5);
    public static final Duration MENU = Duration.ofSeconds(6);
    public static final Duration LONG = Duration.ofSeconds(10);

    private Timeouts() {
    }
}
