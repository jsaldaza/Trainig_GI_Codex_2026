package co.com.training_GI.support;

public enum ProductItem {
    SAUCE_LABS_BIKE_LIGHT("Sauce Labs Bike Light", 0),
    SAUCE_LABS_BOLT_T_SHIRT("Sauce Labs Bolt T-Shirt", 1),
    SAUCE_LABS_ONESIE("Sauce Labs Onesie", 2),
    TEST_ALL_THE_THINGS_T_SHIRT_RED("Test.allTheThings() T-Shirt (Red)", 3),
    SAUCE_LABS_BACKPACK("Sauce Labs Backpack", 4),
    SAUCE_LABS_FLEECE_JACKET("Sauce Labs Fleece Jacket", 5);

    private final String label;
    private final int id;

    ProductItem(String name, int id) {
        this.label = name;
        this.id = id;
    }

    public String label() {
        return label;
    }

    public int id() {
        return id;
    }

    public static ProductItem fromName(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        for (ProductItem item : values()) {
            if (item.label.equals(normalized)) {
                return item;
            }
        }
        return null;
    }

    public static Integer idForName(String value) {
        ProductItem item = fromName(value);
        return item == null ? null : item.id;
    }
}
