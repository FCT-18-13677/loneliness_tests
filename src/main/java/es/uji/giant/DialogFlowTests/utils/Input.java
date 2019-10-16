package es.uji.giant.DialogFlowTests.utils;

public class Input {

    private static boolean isValid (String input, String[] validAnswers) {
        for (String validAnswer : validAnswers) {
            if (validAnswer.equals(input.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidSex(String input) {
        String[] validAnswers = {"masculino", "femenino", "otro", "otros", "prefiero no contestar"};
        return isValid(input, validAnswers);
    }

    public static boolean isValidAge(String input) {
        if (isNumeric(input)) {
            int age = Integer.valueOf(input);
            if (age >= 18 && age <= 105) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String input)
    {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidAlone(String input) {
        String[] validAnswers = {"si", "no", "sí"};
        return isValid(input, validAnswers);
    }

    public static boolean isValidJong (String input) {
        String[] validAnswers = {"si", "no", "más o menos", "sí", "mas o menos"};
        return isValid(input, validAnswers);
    }

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
