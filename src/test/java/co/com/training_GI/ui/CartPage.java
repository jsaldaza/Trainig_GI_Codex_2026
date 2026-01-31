package co.com.training_GI.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CartPage {

    public static final Target CART_LIST = Target.the("cart list")
            .locatedBy(".cart_list");

    public static final Target CART_ITEMS = Target.the("cart items")
            .locatedBy(".cart_item");

    public static Target removeButtonFor(String productName) {
        return Target.the("remove button for " + productName)
                .locatedBy("//div[contains(@class,'cart_item')]" +
                        "[.//div[contains(@class,'inventory_item_name') and normalize-space()='{0}']]" +
                        "//button[normalize-space()='Remove']")
                .of(productName);
    }

    public static Target cartItemName(String productName) {
        return Target.the("cart item " + productName)
                .locatedBy("//div[contains(@class,'cart_item')]" +
                        "//div[contains(@class,'inventory_item_name') and normalize-space()='{0}']")
                .of(productName);
    }

    public static final Target CONTINUE_SHOPPING = Target.the("continue shopping button")
            .locatedBy("#continue-shopping");
}
