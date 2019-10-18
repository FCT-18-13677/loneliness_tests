package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
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
        if(activeQuestionnaries.containsKey(sessionId)) {
            activeQuestionnaries.get(sessionId).addAnswer(parameter.toLowerCase());
            activeQuestionnaries.get(sessionId).calculateJongScore();
            activeQuestionnaries.get(sessionId).calculateUCLAScore();
            activeQuestionnaries.get(sessionId).createDiagnosis();
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
    public String getWrongOutput() {
        return Constants.NOT_VALID_UCLA_ANSWER;
    }
}
