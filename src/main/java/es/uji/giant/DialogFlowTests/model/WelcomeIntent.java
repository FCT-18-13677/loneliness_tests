package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;

import java.util.Map;

public class WelcomeIntent extends Intent {

    private GoogleCloudDialogflowV2Context outputContext;

    WelcomeIntent () {
        outputContext = new GoogleCloudDialogflowV2Context();
        outputContext.setLifespanCount(1);
    }

    @Override
    public boolean isValidInput(String input) {
        return true;
    }

    @Override
    public GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session) {
        String sessionId = session.split("/")[4];
        if (activeQuestionnaries.containsKey(session)) {
            activeQuestionnaries.get(session).setCity(parameter);
        } else {
            Questionnarie questionnarie = new Questionnarie();
            questionnarie.setCity(parameter);
            activeQuestionnaries.put(session, questionnarie);
        }
        outputContext.setName(session + "/contexts/sex");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2Context returnContext(String session) {
        return null;
    }

    @Override
    public GoogleCloudDialogflowV2IntentMessage getReturnFulfillmentMessage() {
        return null;
    }

    @Override
    public String getWrongOutput() {
        return null;
    }
}
