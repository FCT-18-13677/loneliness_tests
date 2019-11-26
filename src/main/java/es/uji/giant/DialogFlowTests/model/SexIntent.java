package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestion;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestions;
import es.uji.giant.DialogFlowTests.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SexIntent extends Intent {

    private GoogleCloudDialogflowV2Context outputContext;

    SexIntent () {
        outputContext = new GoogleCloudDialogflowV2Context();
        outputContext.setLifespanCount(1);
    }

    @Override
    public boolean isValidInput(String input) {
        String[] validAnswers = {"masculino", "femenino", "otro", "otros", "prefiero no contestar"};
        return isValid(input, validAnswers);
    }

    @Override
    public GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session) {
        String sessionId = session.split("/")[4];
        if (activeQuestionnaries.containsKey(sessionId)) {
            activeQuestionnaries.get(sessionId).setSex(parameter);
        } else {
            Questionnarie questionnarie = new Questionnarie();
            questionnarie.setId(sessionId);
            questionnarie.setSex(parameter.toLowerCase());
            activeQuestionnaries.put(sessionId, questionnarie);
        }
        outputContext.setName(session + "/contexts/age");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2Context returnContext(String session) {
        outputContext.setName(session + "/contexts/sex");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2IntentMessage getReturnFulfillmentMessage() {
        GoogleCloudDialogflowV2IntentMessage message = new GoogleCloudDialogflowV2IntentMessage();
        message.setPlatform("ACTIONS_ON_GOOGLE");
        GoogleCloudDialogflowV2IntentMessageSuggestions suggestions = new GoogleCloudDialogflowV2IntentMessageSuggestions();

        GoogleCloudDialogflowV2IntentMessageSuggestion s1 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s1.put("title", "Femenino");

        GoogleCloudDialogflowV2IntentMessageSuggestion s2 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s2.put("title", "Masculino");

        GoogleCloudDialogflowV2IntentMessageSuggestion s3 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s3.put("title", "Otro");

        GoogleCloudDialogflowV2IntentMessageSuggestion s4 = new GoogleCloudDialogflowV2IntentMessageSuggestion();
        s4.put("title", "Prefiero no contestar");

        List<GoogleCloudDialogflowV2IntentMessageSuggestion> suggestionList = new ArrayList<>();
        suggestionList.add(s1);
        suggestionList.add(s2);
        suggestionList.add(s3);
        suggestionList.add(s4);
        suggestions.setSuggestions(suggestionList);

        message.setSuggestions(suggestions);

        return message;
    }

    @Override
    public String getWrongOutput() {
        return Constants.NOT_VALID_SEX_ANSWER;
    }
}
