package hu.miroszlav.shoppinglistapplication.validator;


public class LoginValidator {

    public static boolean validatePassword(String password) {
        return !isNullOrEmpty(password);
    }

    public static boolean validateUserName(String userName) {
        return !isNullOrEmpty(userName);
    }

    private static boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }
}
