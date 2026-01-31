package co.com.training_GI.tasks;

import co.com.training_GI.ui.MenuPage;
import net.serenitybdd.screenplay.Task;
import co.com.training_GI.interactions.ClickSafely;

public class SelectMenuOption {

    private SelectMenuOption() {
    }

    public static Task named(String option) {
        return Task.where("{0} selects the menu option",
                ClickSafely.on(MenuPage.menuOption(option))
        );
    }
}
