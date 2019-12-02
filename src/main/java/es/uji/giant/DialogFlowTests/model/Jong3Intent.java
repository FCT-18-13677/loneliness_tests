package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;
import es.uji.giant.DialogFlowTests.utils.Constants;

import java.util.Map;

public class Jong3Intent extends Intent {

    private GoogleCloudDialogflowV2Context outputContext;

    Jong3Intent () {
        outputContext = new GoogleCloudDialogflowV2Context();
        outputContext.setLifespanCount(1);
    }

    @Override
    public boolean isValidInput(String input) {
        String[] validAnswers = {"si", "no", "más o menos", "sí", "mas o menos"};
        return isValid(input, validAnswers);
    }

    @Override
    public GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session) {
        String sessionId = session.split("/")[4];
        if (activeQuestionnaries.containsKey(session)) {
            activeQuestionnaries.get(session).addAnswer(parameter.toLowerCase());
        }
        outputContext.setName(session + "/contexts/val4");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2Context returnContext(String session) {
        outputContext.setName(session + "/contexts/val3");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2IntentMessage getReturnFulfillmentMessage() {
        return generateJongFulfillment();
    }

    @Override
    public String getWrongOutput() {
        return Constants.NOT_VALID_JONG_ANSWER;
    }
}
