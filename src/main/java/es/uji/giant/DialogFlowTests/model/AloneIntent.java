package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import es.uji.giant.DialogFlowTests.utils.Constants;

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
    public String getWrongOutput() {
        return Constants.NOT_VALID_ALONE_ANSWER;
    }
}
