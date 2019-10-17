package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import es.uji.giant.DialogFlowTests.utils.Constants;

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
    public String getWrongOutput() {
        return Constants.NOT_VALID_SEX_ANSWER;
    }
}
