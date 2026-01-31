package co.com.training_GI.ui;

import net.serenitybdd.screenplay.targets.Target;

public class ProductDetailsPage {

    public static final Target PRODUCT_NAME = Target.the("product name")
            .locatedBy(".inventory_details_name");

    public static final Target ADD_TO_CART = Target.the("add to cart button")
            .locatedBy("//button[contains(@class,'btn_inventory') and normalize-space()='Add to cart']");

    public static final Target REMOVE_BUTTON = Target.the("remove button")
            .locatedBy("//button[contains(@class,'btn_inventory') and normalize-space()='Remove']");

    public static final Target BACK_TO_PRODUCTS = Target.the("back to products button")
            .locatedBy("#back-to-products");
}
