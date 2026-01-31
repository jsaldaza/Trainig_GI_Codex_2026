package co.com.training_GI.ui;

import net.serenitybdd.screenplay.targets.Target;

public class ProductsPage {

    public static final Target TITLE = Target.the("products page title")
            .locatedBy(".title");

    public static final Target FIRST_ADD_TO_CART = Target.the("first add to cart button")
            .locatedBy(".inventory_item:first-of-type button.btn_inventory");

    public static final Target CART_ICON = Target.the("shopping cart icon")
            .locatedBy(".shopping_cart_link");

    public static final Target CART_BADGE = Target.the("shopping cart badge")
            .locatedBy(".shopping_cart_badge");

    public static Target addToCartButtonFor(String productName) {
        return Target.the("add to cart button for " + productName)
                .locatedBy("//div[contains(@class,'inventory_item')]" +
                        "[.//div[contains(@class,'inventory_item_name') and normalize-space()='{0}']]" +
                        "//button[contains(@class,'btn_inventory')]")
                .of(productName);
    }

    public static Target removeButtonFor(String productName) {
        return Target.the("remove button for " + productName)
                .locatedBy("//div[contains(@class,'inventory_item')]" +
                        "[.//div[contains(@class,'inventory_item_name') and normalize-space()='{0}']]" +
                        "//button[normalize-space()='Remove']")
                .of(productName);
    }

    public static Target productLinkFor(String productName) {
        return Target.the("product link " + productName)
                .locatedBy("//div[contains(@class,'inventory_item_name') and normalize-space()='{0}']")
                .of(productName);
    }
}
