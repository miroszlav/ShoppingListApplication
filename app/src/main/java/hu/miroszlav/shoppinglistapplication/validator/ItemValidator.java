package hu.miroszlav.shoppinglistapplication.validator;


public class ItemValidator {

    public static boolean validateName(String name) {
        return name != null && !name.isEmpty();
    }

    public static boolean validateQuantity(String quantity) {
        return quantity != null && quantity.matches("[0-9]+") && Integer.valueOf(quantity) > 0;
    }
}
