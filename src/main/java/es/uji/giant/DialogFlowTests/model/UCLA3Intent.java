package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2IntentMessage;
import es.uji.giant.DialogFlowTests.utils.Constants;

import java.util.Map;

public class UCLA3Intent extends Intent {

    private GoogleCloudDialogflowV2Context outputContext;

    UCLA3Intent () {
        outputContext = new GoogleCloudDialogflowV2Context();
        outputContext.setLifespanCount(1);
    }

    @Override
    public boolean isValidInput(String input) {
        String[] validAnswers = {"casi nunca", "algunas veces", "a menudo"};
        return isValid(input, validAnswers);
    }

    @Override
    public GoogleCloudDialogflowV2Context fillInformation(Map<String, Questionnarie> activeQuestionnaries, String parameter, String session) {
        String sessionId = session.split("/")[4];
        if(activeQuestionnaries.containsKey(session)) {
            activeQuestionnaries.get(session).addAnswer(parameter.toLowerCase());
            activeQuestionnaries.get(session).calculateJongScore();
            activeQuestionnaries.get(session).calculateUCLAScore();
            activeQuestionnaries.get(session).createDiagnosis();
        }
        outputContext.setName(session + "/contexts/user_comment");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2Context returnContext(String session) {
        outputContext.setName(session + "/contexts/val9");
        return outputContext;
    }

    @Override
    public GoogleCloudDialogflowV2IntentMessage getReturnFulfillmentMessage() {
        return generateUCLAFulfillment();
    }

    @Override
    public String getWrongOutput() {
        return Constants.NOT_VALID_UCLA_ANSWER;
    }
}
