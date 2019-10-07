package es.uji.giant.DialogFlowTests.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Input {
    private static Logger logger = LoggerFactory.getLogger(Input.class);

    public static int calculateScore(Map<String, String> params) {
        int sum = 0;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sum += Integer.valueOf(entry.getValue());
        }

        return sum * 4;
    }

    public static boolean checkUserInput(String input) {
        boolean isCorrectNumber = false;

        if (input.length() == 1 && input.charAt(0) >= '1' && input.charAt(0) <= '5')
            isCorrectNumber = true;

        return isCorrectNumber;
    }

    private static boolean isValid (String input, String[] validAnswers) {
        for (String validAnswer : validAnswers) {
            if (validAnswer.equals(input.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidSex(String input) {
        String[] validAnswers = {"macho", "hombre", "mujer", "hembra", "varón", "varon"};
        return isValid(input, validAnswers);
    }

    public static boolean isValidAge(String input) {
        try {
            int age = Integer.valueOf(input);
            if (age >= 18 && age <= 105) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
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

    public static String isWritenNumber(String text) {
        String answer = "";
        text = text.toLowerCase();
        switch(text) {
            case "uno":
                answer = "1";
                break;
            case "dos":
                answer = "2";
                break;
            case "tres":
                answer = "3";
                break;
            case "cuatro":
                answer = "4";
                break;
            case "cinco":
                answer = "5";
                break;
            default:
                answer = text;
        }
        return answer;
    }
}
