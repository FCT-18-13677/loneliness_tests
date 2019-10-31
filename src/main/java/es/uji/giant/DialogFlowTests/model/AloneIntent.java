package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestion;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestions;
import es.uji.giant.DialogFlowTests.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AloneIntent extends Intent {

    private GoogleCloudDialogflowV2Context outputContext;

    AloneIntent () {
        outputContext = new GoogleCloudDialogflowV2Context();
        outputContext.setLifespanCount(1);
    }

    @Override
    public boolean isValidInput(String input) {
        String[] validAnswers = {"si", "no", "sí", "prefiero no contestar"};
        return isValid(input, validAnswers);
    }

    @Override
    public GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session) {
        String sessionId = session.split("/")[4];
        if (activeQuestionnaries.containsKey(sessionId)) {
            activeQuestionnaries.get(sessionId).setLiveAlone(parameter.toLowerCase().equals("si") || parameter.toLowerCase().equals("sí"));
        }
        outputContext.setName(session + "/contexts/val1");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2Context returnContext(String session) {
        outputContext.setName(session + "/contexts/solo");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2IntentMessage getReturnFulfillmentMessage() {
        GoogleCloudDialogflowV2IntentMessage message = new GoogleCloudDialogflowV2IntentMessage();
        message.setPlatform("ACTIONS_ON_GOOGLE");
        GoogleCloudDialogflowV2IntentMessageSuggestions suggestions = new GoogleCloudDialogflowV2IntentMessageSuggestions();

        GoogleCloudDialogflowV2IntentMessageSuggestion s1 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s1.put("title", "Sí");

        GoogleCloudDialogflowV2IntentMessageSuggestion s2 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s2.put("title", "No");

        GoogleCloudDialogflowV2IntentMessageSuggestion s3 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s3.put("title", "Prefiero no contestar");

        List<GoogleCloudDialogflowV2IntentMessageSuggestion> suggestionList = new ArrayList<>();
        suggestionList.add(s1);
        suggestionList.add(s2);
        suggestionList.add(s3);
        suggestions.setSuggestions(suggestionList);

        message.setSuggestions(suggestions);

        return message;
    }

    @Override
    public String getWrongOutput() {
        return Constants.NOT_VALID_ALONE_ANSWER;
    }
}
