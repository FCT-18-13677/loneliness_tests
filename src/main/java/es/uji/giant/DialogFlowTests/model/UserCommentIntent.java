package es.uji.giant.DialogFlowTests.model;

import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;

import java.util.Map;

public class UserCommentIntent extends Intent {

    private GoogleCloudDialogflowV2Context outputContext;

    UserCommentIntent () {
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
        if (activeQuestionnaries.containsKey(sessionId)) {
            activeQuestionnaries.get(sessionId).setUserComments(parameter);
            activeQuestionnaries.get(sessionId).setTimestamp(System.currentTimeMillis());
        }
        return null;
    }

    @Override
    public GoogleCloudDialogflowV2Context returnContext(String session) {
        outputContext.setName(session + "/contexts/user_comment");
        return outputContext;
    }

    @Override
    public String getWrongOutput() {
        return null;
    }
}
