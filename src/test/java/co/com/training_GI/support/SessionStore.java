package co.com.training_GI.support;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.openqa.selenium.Cookie;

public class SessionStore {

    private static Set<Cookie> cookies = Collections.emptySet();

    private SessionStore() {
    }

    public static boolean hasCookies() {
        return cookies != null && !cookies.isEmpty();
    }

    public static Set<Cookie> getCookies() {
        return cookies;
    }

    public static void saveCookies(Set<Cookie> newCookies) {
        cookies = new HashSet<>(newCookies);
    }
}
