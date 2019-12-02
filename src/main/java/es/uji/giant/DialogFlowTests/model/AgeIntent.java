package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestion;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestions;
import es.uji.giant.DialogFlowTests.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AgeIntent extends Intent {

    private GoogleCloudDialogflowV2Context outputContext;

    AgeIntent () {
        outputContext = new GoogleCloudDialogflowV2Context();
        outputContext.setLifespanCount(1);
    }

    @Override
    public boolean isValidInput(String input) {
        if (input.toLowerCase().equals("prefiero no contestar")) return true;

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

    @Override
    public GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session) {
        String sessionId = session.split("/")[4];
        if (activeQuestionnaries.containsKey(session)) {
            // Guarda la edad. Si el usuario no lo ha especificado, la edad será "0".
            if (!parameter.toLowerCase().equals("prefiero no contestar")) {
                activeQuestionnaries.get(session).setAge(Integer.valueOf(parameter));
            }
        }
        outputContext.setName(session + "/contexts/solo");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2Context returnContext(String session) {
        outputContext.setName(session + "/contexts/age");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2IntentMessage getReturnFulfillmentMessage() {
        GoogleCloudDialogflowV2IntentMessage message = new GoogleCloudDialogflowV2IntentMessage();
        message.setPlatform("ACTIONS_ON_GOOGLE");
        GoogleCloudDialogflowV2IntentMessageSuggestions suggestions = new GoogleCloudDialogflowV2IntentMessageSuggestions();

        GoogleCloudDialogflowV2IntentMessageSuggestion s = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s.put("title", "Prefiero no contestar");

        List<GoogleCloudDialogflowV2IntentMessageSuggestion> suggestionList = new ArrayList<>();
        suggestionList.add(s);
        suggestions.setSuggestions(suggestionList);

        message.setSuggestions(suggestions);

        return message;
    }

    @Override
    public String getWrongOutput() {
        return Constants.NOT_VALID_AGE_ANSWER;
    }

    private static boolean isNumeric(String input)
    {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
