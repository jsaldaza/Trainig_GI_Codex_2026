package co.com.training_GI.support;

public enum MenuOption {
    ALL_ITEMS("All Items"),
    ABOUT("About"),
    LOGOUT("Logout"),
    RESET_APP_STATE("Reset App State");

    private final String label;

    MenuOption(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static MenuOption from(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim().toLowerCase();
        for (MenuOption option : values()) {
            if (option.label.toLowerCase().equals(normalized)) {
                return option;
            }
        }
        return null;
    }
}
