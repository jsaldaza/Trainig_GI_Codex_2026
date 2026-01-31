package co.com.training_GI.support;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.openqa.selenium.Cookie;

public class SessionStore {

    private static final ThreadLocal<Set<Cookie>> COOKIES =
            ThreadLocal.withInitial(Collections::emptySet);

    private SessionStore() {
    }

    public static boolean hasCookies() {
        Set<Cookie> cookies = COOKIES.get();
        return cookies != null && !cookies.isEmpty();
    }

    public static Set<Cookie> getCookies() {
        return COOKIES.get();
    }

    public static void saveCookies(Set<Cookie> newCookies) {
        COOKIES.set(new HashSet<>(newCookies));
    }

    public static void clear() {
        COOKIES.set(Collections.emptySet());
    }
}
