package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestion;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Intent {

    public abstract boolean isValidInput(String input);
    public abstract GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session);
    public abstract GoogleCloudDialogflowV2Context returnContext(String session);
    public abstract GoogleCloudDialogflowV2IntentMessage getReturnFulfillmentMessage();
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

    public GoogleCloudDialogflowV2IntentMessage generateJongFulfillment() {
        GoogleCloudDialogflowV2IntentMessage message = new GoogleCloudDialogflowV2IntentMessage();
        message.setPlatform("ACTIONS_ON_GOOGLE");
        GoogleCloudDialogflowV2IntentMessageSuggestions suggestions = new GoogleCloudDialogflowV2IntentMessageSuggestions();

        GoogleCloudDialogflowV2IntentMessageSuggestion s1 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s1.put("title", "Sí");

        GoogleCloudDialogflowV2IntentMessageSuggestion s2 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s2.put("title", "No");

        GoogleCloudDialogflowV2IntentMessageSuggestion s3 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s3.put("title", "Más o menos");

        List<GoogleCloudDialogflowV2IntentMessageSuggestion> suggestionList = new ArrayList<>();
        suggestionList.add(s1);
        suggestionList.add(s2);
        suggestionList.add(s3);
        suggestions.setSuggestions(suggestionList);

        message.setSuggestions(suggestions);

        return message;
    }

    public GoogleCloudDialogflowV2IntentMessage generateUCLAFulfillment() {
        GoogleCloudDialogflowV2IntentMessage message = new GoogleCloudDialogflowV2IntentMessage();
        message.setPlatform("ACTIONS_ON_GOOGLE");
        GoogleCloudDialogflowV2IntentMessageSuggestions suggestions = new GoogleCloudDialogflowV2IntentMessageSuggestions();

        GoogleCloudDialogflowV2IntentMessageSuggestion s1 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s1.put("title", "Casi nunca");

        GoogleCloudDialogflowV2IntentMessageSuggestion s2 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s2.put("title", "Algunas veces");

        GoogleCloudDialogflowV2IntentMessageSuggestion s3 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s3.put("title", "A menudo");

        List<GoogleCloudDialogflowV2IntentMessageSuggestion> suggestionList = new ArrayList<>();
        suggestionList.add(s1);
        suggestionList.add(s2);
        suggestionList.add(s3);
        suggestions.setSuggestions(suggestionList);

        message.setSuggestions(suggestions);

        return message;
    }
}
