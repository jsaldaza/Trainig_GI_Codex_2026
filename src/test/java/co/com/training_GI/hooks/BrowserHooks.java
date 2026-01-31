package co.com.training_GI.hooks;

import io.cucumber.java.Before;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BrowserHooks {

    private static String selectedBrowser;

    @Before(order = 0)
    public void selectRandomBrowser() {
        List<String> browsers = List.of("chrome", "edge", "firefox");
        String configured = System.getProperty("webdriver.driver");
        if (selectedBrowser == null) {
            selectedBrowser = configured == null || configured.isBlank()
                    ? browsers.get(ThreadLocalRandom.current().nextInt(browsers.size()))
                    : configured;
        }
        System.setProperty("webdriver.driver", selectedBrowser);
        System.out.println("Running tests on browser: " + selectedBrowser);
    }
}
