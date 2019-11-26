package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestion;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessageSuggestions;
import es.uji.giant.DialogFlowTests.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CityIntent extends Intent {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private GoogleCloudDialogflowV2Context outputContext;

    CityIntent() {
        outputContext = new GoogleCloudDialogflowV2Context();
        outputContext.setLifespanCount(1);
    }

    @Override
    public boolean isValidInput(String input) {
        List<String> codes = Arrays.asList(Constants.CITIES);
        codes.replaceAll(String::toLowerCase);
        return codes.contains(input.toLowerCase());
    }

    @Override
    public GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session) {
        String sessionId = session.split("/")[4];
        if (activeQuestionnaries.containsKey(sessionId)) {
            activeQuestionnaries.get(sessionId).setCity(parameter);
        } else {
            Questionnarie questionnarie = new Questionnarie();
            questionnarie.setCity(parameter);
            activeQuestionnaries.put(sessionId, questionnarie);
        }
        outputContext.setName(session + "/contexts/val1");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2Context returnContext(String session) {
        outputContext.setName(session + "/contexts/city");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2IntentMessage getReturnFulfillmentMessage() {
        /*GoogleCloudDialogflowV2IntentMessage message = new GoogleCloudDialogflowV2IntentMessage();
        message.setPlatform("ACTIONS_ON_GOOGLE");
        return message;*/
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
        return Constants.NOT_VALID_CITY_ANSWER;
    }
}
