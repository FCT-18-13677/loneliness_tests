package es.uji.giant.DialogFlowTests.utils;

public class Input {
    public static boolean isValidUCLA (String input) {
        String[] validAnswers = {"casi nunca", "algunas veces", "a menudo"};
        return isValid(input, validAnswers);
    }

    public static boolean userWantsToCancel(String userInput) {
        boolean cancel = false;
        if (userInput.equals("cancel") || userInput.equals("cancelar")) {
            cancel = true;
        }
        return cancel;
    }
}
