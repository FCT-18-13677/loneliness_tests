package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;

import java.util.Map;

public abstract class Intent {

    public abstract boolean isValidInput(String input);
    public abstract GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session);
    public abstract GoogleCloudDialogflowV2Context returnContext(String session);
    public abstract String getWrongOutput();

    boolean isValid (String input, String[] validAnswers) {
        for (String validAnswer : validAnswers) {
            if (validAnswer.equals(input.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean userWantsToCancel(String userInput) {
        boolean cancel = false;
        if (userInput.equals("cancel") || userInput.equals("cancelar")) {
            cancel = true;
        }
        return cancel;
    }
}
