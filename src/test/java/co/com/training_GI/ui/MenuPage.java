package co.com.training_GI.ui;

import co.com.training_GI.support.MenuOption;
import net.serenitybdd.screenplay.targets.Target;

public class MenuPage {

    public static final Target HAMBURGER_BUTTON = Target.the("hamburger menu button")
            .locatedBy("#react-burger-menu-btn");

    public static Target menuOption(String option) {
        MenuOption known = MenuOption.from(option);
        if (known != null) {
            return menuOption(known);
        }
        return Target.the("menu option " + option)
                .locatedBy("//nav//a[normalize-space()='{0}']")
                .of(option);
    }

    public static Target menuOption(MenuOption option) {
        if (option == null) {
            return menuOption("");
        }
        String id;
        switch (option) {
            case ALL_ITEMS:
                id = "inventory_sidebar_link";
                break;
            case ABOUT:
                id = "about_sidebar_link";
                break;
            case LOGOUT:
                id = "logout_sidebar_link";
                break;
            case RESET_APP_STATE:
                id = "reset_sidebar_link";
                break;
            default:
                return Target.the("menu option " + option.label())
                        .locatedBy("//nav//a[normalize-space()='{0}']")
                        .of(option.label());
        }
        return Target.the("menu option " + option.label())
                .locatedBy("#" + id);
    }
}
