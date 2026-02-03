package co.com.training_GI.hooks;

import co.com.training_GI.support.SessionStore;
import io.cucumber.java.AfterAll;

public class SuiteHooks {

    @AfterAll
    public static void cleanSession() {
        SessionStore.clearAll();
    }
}
