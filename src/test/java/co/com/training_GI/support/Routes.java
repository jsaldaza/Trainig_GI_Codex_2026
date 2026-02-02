package co.com.training_GI.support;

public final class Routes {

    public static final String ROOT = "/";
    public static final String CART = "/cart.html";
    public static final String INVENTORY = "/inventory.html";

    private Routes() {
    }

    public static String inventoryItem(int id) {
        return "/inventory-item.html?id=" + id;
    }
}
