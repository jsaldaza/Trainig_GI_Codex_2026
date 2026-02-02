package co.com.training_GI.tasks;

import co.com.training_GI.support.MenuOption;
import net.serenitybdd.screenplay.Task;

public class UseMenu {

    private UseMenu() {
    }

    public static Task option(MenuOption option) {
        String label = option == null ? "" : option.label();
        return Task.where("{0} selects menu option " + label,
                OpenMenu.now(),
                SelectMenuOption.named(option)
        );
    }

    public static Task option(String option) {
        return Task.where("{0} selects menu option " + option,
                OpenMenu.now(),
                SelectMenuOption.named(option)
        );
    }
}
