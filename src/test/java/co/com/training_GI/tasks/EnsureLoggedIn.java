package co.com.training_GI.tasks;

import co.com.training_GI.support.AppConfig;
import co.com.training_GI.support.Credentials;
import co.com.training_GI.support.Routes;
import co.com.training_GI.support.SessionStore;
import co.com.training_GI.support.Timeouts;
import co.com.training_GI.ui.LoginPage;
import co.com.training_GI.ui.ProductsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class EnsureLoggedIn implements Task {

    private final String username;
    private final String password;

    private EnsureLoggedIn(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Task as(String username, String password) {
        return new EnsureLoggedIn(username, password);
    }

    public static Task withDefaultCredentials() {
        return new EnsureLoggedIn(Credentials.username(), Credentials.password());
    }

    @Override
    public void performAs(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        if (AppConfig.sessionReuse()) {
            if (SessionStore.hasCookies()) {
                driver.navigate().to(AppConfig.baseUrl());
                for (Cookie cookie : SessionStore.getCookies()) {
                    driver.manage().addCookie(cookie);
                }
                driver.navigate().to(AppConfig.baseUrl() + Routes.INVENTORY);
                try {
                    actor.attemptsTo(WaitUntil.the(ProductsPage.TITLE, isVisible())
                            .forNoMoreThan(Timeouts.MEDIUM));
                    return;
                } catch (RuntimeException ignored) {
                    // If cookies are stale, fall back to UI login.
                }
            }
        }

        actor.attemptsTo(
                Open.browserOn().the(LoginPage.class),
                Login.withCredentials(username, password),
                WaitUntil.the(ProductsPage.TITLE, isVisible())
                        .forNoMoreThan(Timeouts.LONG)
        );
        if (AppConfig.sessionReuse()) {
            Set<Cookie> cookies = driver.manage().getCookies();
            SessionStore.saveCookies(cookies);
        }
    }
}
