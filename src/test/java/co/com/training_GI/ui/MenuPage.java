package co.com.training_GI.ui;

import net.serenitybdd.screenplay.targets.Target;

public class MenuPage {

    public static final Target HAMBURGER_BUTTON = Target.the("hamburger menu button")
            .locatedBy("#react-burger-menu-btn");

    public static Target menuOption(String option) {
        String normalized = option == null ? "" : option.trim().toLowerCase();
        String id;
        switch (normalized) {
            case "all items":
                id = "inventory_sidebar_link";
                break;
            case "about":
                id = "about_sidebar_link";
                break;
            case "logout":
                id = "logout_sidebar_link";
                break;
            case "reset app state":
                id = "reset_sidebar_link";
                break;
            default:
                return Target.the("menu option " + option)
                        .locatedBy("//nav//a[normalize-space()='{0}']")
                        .of(option);
        }
        return Target.the("menu option " + option)
                .locatedBy("#" + id);
    }
}
