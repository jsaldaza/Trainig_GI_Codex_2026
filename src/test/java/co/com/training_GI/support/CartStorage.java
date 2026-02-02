package co.com.training_GI.support;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;

public final class CartStorage {

    private static final String CART_KEY = "cart-contents";

    private CartStorage() {
    }

    public static void addItem(Actor actor, int itemId) {
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript(
                "var key=arguments[1];" +
                        "var items = JSON.parse(window.localStorage.getItem(key) || '[]');" +
                        "if (items.indexOf(arguments[0]) === -1) { items.push(arguments[0]); }" +
                        "window.localStorage.setItem(key, JSON.stringify(items));",
                itemId, CART_KEY
        );
    }

    public static void removeItem(Actor actor, int itemId) {
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript(
                "var key=arguments[1];" +
                        "var items = JSON.parse(window.localStorage.getItem(key) || '[]');" +
                        "var idx = items.indexOf(arguments[0]);" +
                        "if (idx !== -1) { items.splice(idx, 1); }" +
                        "window.localStorage.setItem(key, JSON.stringify(items));",
                itemId, CART_KEY
        );
    }

    public static void resetCart(Actor actor) {
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript("window.localStorage.setItem(arguments[0],'[]');", CART_KEY);
    }

    public static void clearAll(Actor actor) {
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript("window.localStorage.clear();");
    }

    public static void reload(Actor actor) {
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript("window.location.reload();");
    }
}
