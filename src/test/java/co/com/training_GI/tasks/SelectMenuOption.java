package co.com.training_GI.tasks;

import co.com.training_GI.ui.MenuPage;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.JavaScriptClick;

public class SelectMenuOption {

    private SelectMenuOption() {
    }

    public static Task named(String option) {
        return Task.where("{0} selects the menu option",
                JavaScriptClick.on(MenuPage.menuOption(option))
        );
    }
}
