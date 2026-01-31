package co.com.training_GI.hooks;

import io.cucumber.java.Before;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BrowserHooks {

    private static String selectedBrowser;

    @Before(order = 0)
    public void selectRandomBrowser() {
        String configured = System.getProperty("webdriver.driver");
        boolean randomEnabled = Boolean.parseBoolean(System.getProperty("random.browser", "false"));
        if (selectedBrowser == null) {
            if (configured != null && !configured.isBlank()) {
                selectedBrowser = configured;
            } else if (randomEnabled) {
                List<String> browsers = List.of("chrome", "edge", "firefox");
                selectedBrowser = browsers.get(ThreadLocalRandom.current().nextInt(browsers.size()));
            } else {
                selectedBrowser = "chrome";
            }
        }
        System.setProperty("webdriver.driver", selectedBrowser);
        System.out.println("Running tests on browser: " + selectedBrowser);
    }
}
