package es.uji.giant.DialogFlowTests.controller;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2.model.*;
import es.uji.giant.DialogFlowTests.listener.ClearMapListener;
import es.uji.giant.DialogFlowTests.model.Intent;
import es.uji.giant.DialogFlowTests.model.IntentFactory;
import es.uji.giant.DialogFlowTests.model.Questionnarie;
import es.uji.giant.DialogFlowTests.repository.QuestionnarieDao;
import es.uji.giant.DialogFlowTests.service.ClearActiveQuestionnarieService;
import es.uji.giant.DialogFlowTests.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class DialogflowController extends HttpServlet implements ClearMapListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, Questionnarie> activeQuestionnaries;
    private QuestionnarieDao questionnarieDao;
    private IntentFactory factory;
    private ClearActiveQuestionnarieService service;

    @Autowired
    public DialogflowController(QuestionnarieDao questionnarieDao, ClearActiveQuestionnarieService service) {
        this.questionnarieDao = questionnarieDao;
        factory = new IntentFactory();
        this.service = service;
        service.setListener(this);
        activeQuestionnaries = new HashMap<>();
    }

    @PostMapping("/dialogflow")
    public ResponseEntity<?> dialogflowWebhook(@RequestBody String requestStr, HttpServletRequest servletRequest) {
        try {
            // Read request and create response
            GoogleCloudDialogflowV2WebhookResponse response = new GoogleCloudDialogflowV2WebhookResponse();
            GoogleCloudDialogflowV2WebhookRequest request = JacksonFactory.getDefaultInstance().createJsonParser(requestStr).parse(GoogleCloudDialogflowV2WebhookRequest.class);

            // Some variables
            String output = "", parameter;
            GoogleCloudDialogflowV2Context outputContext = null;
            GoogleCloudDialogflowV2EventInput eventInput = null;
            boolean thereIsContext = false;
            boolean thereIsEvent = false;
            Map<String, Object> parameters = request.getQueryResult().getParameters();      // Get parameters sent
            String activeIntent = request.getQueryResult().getIntent().getDisplayName();    // Get activeIntent name
            parameter = String.valueOf(parameters.get("val"));

            // Check session.
            logger.info("Active Intent: " + activeIntent);
            String session = request.getSession();
            String sessionId = session.split("/")[4];
            logger.info("Active Session: " + session);

            Intent actualIntent = factory.getIntent(activeIntent);

            if (!actualIntent.userWantsToCancel(parameter) && !activeIntent.equals(Constants.USER_COMMENT_INTENT)) { // El usuario no quiere cancelar y no es el último intent...
                thereIsContext = true;
                if (actualIntent.isValidInput(parameter)) {
                    outputContext = actualIntent.fillInformation(activeQuestionnaries, parameter, session);
                    output = request.getQueryResult().getFulfillmentText();
                } else {
                    outputContext = actualIntent.returnContext(session);
                    output = actualIntent.getWrongOutput();
                }

            } else if (activeIntent.equals(Constants.USER_COMMENT_INTENT)) { // Último Intent
                parameter = request.getQueryResult().getQueryText();
                actualIntent.fillInformation(activeQuestionnaries, parameter, session);
                questionnarieDao.insertQuestionnarie(sessionId, activeQuestionnaries.get(sessionId));
                activeQuestionnaries.remove(sessionId);
                output = Constants.FINISHED_CONVERSATION_OUTPUT_ANSWER;

            } else {    // El usuario cancela la conversaión
                thereIsEvent = true;
                output = Constants.CANCEL_CONVERSATION_OUTPUT;
                activeQuestionnaries.remove(sessionId);
                eventInput = sendToWelcomeIntent(session);
            }

            logger.info("\n\nActive Questionnaries: " + activeQuestionnaries.size());

            // Response
            response.setFulfillmentText(output);
            // Esta línea, entre otras cosas, añade las "Suggestion Chips de Google") y añade el output (si no se ñade de esta forma no es suficiente con la
            // línea anterior.
            response.setFulfillmentMessages(request.getQueryResult().getFulfillmentMessages());
            // Output especial Google Asistant
            response.getFulfillmentMessages().get(response.getFulfillmentMessages().size() - 1).getText().setText(new ArrayList<>(Arrays.asList(output)));

            // Output especial para Telegram / VOZ de Google Assistant
            if (!activeIntent.equals(Constants.USER_COMMENT_INTENT) && !activeIntent.equals(Constants.UCLA3_INTENT)) {
                response.getFulfillmentMessages().get(0).getSimpleResponses().getSimpleResponses().get(0).setDisplayText(output);
                response.getFulfillmentMessages().get(0).getSimpleResponses().getSimpleResponses().get(0).setTextToSpeech(output);
            }

            if (thereIsContext)
                response.setOutputContexts(new ArrayList<>(Arrays.asList(outputContext)));
            if (thereIsEvent)
                response.setFollowupEventInput(eventInput);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            logger.info("FaiL!");
            e.printStackTrace();
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private GoogleCloudDialogflowV2EventInput sendToWelcomeIntent(String session) {
        String sessionId = session.split("/")[4];
        GoogleCloudDialogflowV2EventInput eventInput = new GoogleCloudDialogflowV2EventInput();
        eventInput.setName("Welcome");
        activeQuestionnaries.remove(sessionId);
        logger.info("Sesión a borrar -> " + sessionId);
        logger.info("El usuario ha cancelado la conversación");
        return eventInput;
    }

    @Override
    public void clearMap() {
        logger.info("\nEmpezando a borrar:");
        for (String string : activeQuestionnaries.keySet()) {
            logger.info(activeQuestionnaries.remove(string).toString());
        }
    }
}
