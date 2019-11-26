package es.uji.giant.DialogFlowTests.controller;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2.model.*;
import es.uji.giant.DialogFlowTests.model.Intent;
import es.uji.giant.DialogFlowTests.model.IntentFactory;
import es.uji.giant.DialogFlowTests.model.Questionnarie;
import es.uji.giant.DialogFlowTests.repository.QuestionnarieDao;
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
public class DialogflowController extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, Questionnarie> activeQuestionnaries;
    private QuestionnarieDao questionnarieDao;
    private IntentFactory factory;

    @Autowired
    public DialogflowController(QuestionnarieDao questionnarieDao) {
        this.questionnarieDao = questionnarieDao;
        factory = new IntentFactory();
        activeQuestionnaries = new HashMap<>();
    }

    @PostMapping("/dialogflow")
    public ResponseEntity<?> dialogflowWebhook(@RequestBody String requestStr, HttpServletRequest servletRequest) {

        try {
            // Lee el request y crea el Response
            GoogleCloudDialogflowV2WebhookResponse response = new GoogleCloudDialogflowV2WebhookResponse();
            GoogleCloudDialogflowV2WebhookRequest request = JacksonFactory.getDefaultInstance().createJsonParser(requestStr).parse(GoogleCloudDialogflowV2WebhookRequest.class);

            // Algunas variables
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
            //logger.info(request.toPrettyString());

            Intent actualIntent = factory.getIntent(activeIntent);

            if (!activeIntent.equals(Constants.WELCOME_INTENT)) {
                logger.info("\n\nActive Questionnaries: " + activeQuestionnaries.size());

                // Para cualquier Intent excepto el comentario final de usuario
                if (!actualIntent.userWantsToCancel(parameter) && !activeIntent.equals(Constants.USER_COMMENT_INTENT)) { // El usuario no quiere cancelar y no es el último intent...
                    thereIsContext = true;

                    // Si el usuario ha introducido una respuesta válida
                    if (actualIntent.isValidInput(parameter)) {
                        outputContext = actualIntent.fillInformation(activeQuestionnaries, parameter, session);
                        output = request.getQueryResult().getFulfillmentText();

                        response.setFulfillmentMessages(request.getQueryResult().getFulfillmentMessages());
                        // Output especial Google Asistant
                        response.getFulfillmentMessages().get(response.getFulfillmentMessages().size() - 1).getText().setText(new ArrayList<>(Arrays.asList(output)));

                    // Si el usuario NO ha introducido una respuesta válida
                    } else {
                        outputContext = actualIntent.returnContext(session);
                        output = actualIntent.getWrongOutput();

                        if (!activeIntent.equals(Constants.CITY_INTENT)) {
                        GoogleCloudDialogflowV2IntentMessage fulfillmentMessage = actualIntent.getReturnFulfillmentMessage();

                        List<GoogleCloudDialogflowV2IntentMessage> actualMessages = restoreSuggestionChips(request, fulfillmentMessage, output);
                        response.setFulfillmentMessages(actualMessages);
                        }
                    }

                // Si es el último intent (comentario final del usuario)
                } else if (activeIntent.equals(Constants.USER_COMMENT_INTENT)) {
                    parameter = request.getQueryResult().getQueryText();
                    actualIntent.fillInformation(activeQuestionnaries, parameter, session);
                    if (questionnarieDao.insertQuestionnarie(sessionId, activeQuestionnaries.get(sessionId)))
                        logger.info("Questimarie saved -> " + activeQuestionnaries.get(sessionId).toString());
                    activeQuestionnaries.remove(sessionId);
                    output = Constants.FINISHED_CONVERSATION_OUTPUT_ANSWER;

                // El usuario cancela la conversaión
                } else {
                    thereIsEvent = true;
                    output = Constants.CANCEL_CONVERSATION_OUTPUT;
                    activeQuestionnaries.remove(sessionId);
                    eventInput = sendToWelcomeIntent(session);
                }

                // Response
                response.setFulfillmentText(output);
                // Output especial para Telegram / VOZ de Google Assistant
                if (!activeIntent.equals(Constants.USER_COMMENT_INTENT) && !activeIntent.equals(Constants.CITY_INTENT) && !activeIntent.equals(Constants.UCLA3_INTENT)) {
                    response.getFulfillmentMessages().get(0).getSimpleResponses().getSimpleResponses().get(0).setDisplayText(output);
                    response.getFulfillmentMessages().get(0).getSimpleResponses().getSimpleResponses().get(0).setTextToSpeech(output);
                }

                if (thereIsContext)
                    response.setOutputContexts(new ArrayList<>(Arrays.asList(outputContext)));
                if (thereIsEvent)
                    response.setFollowupEventInput(eventInput);

            // Welcome Intent (recoge código de una url
            } /*else {
                List<Object> a = (ArrayList)request.getOriginalDetectIntentRequest().getPayload().get("inputs");
                List<Object> arguments = (ArrayList) ((ArrayMap) a.get(0)).get("arguments");
                if (arguments != null) {
                    ArrayMap<String, String> map = (ArrayMap) arguments.get(0);
                    parameter = map.get("rawText");
                    logger.info("Parametro pasado por URL: " + parameter);
                }
                actualIntent.fillInformation(activeQuestionnaries, parameter, session);
            }*/

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            logger.info("Error en el webhook");
            e.printStackTrace();
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private List<GoogleCloudDialogflowV2IntentMessage> restoreSuggestionChips(GoogleCloudDialogflowV2WebhookRequest request, GoogleCloudDialogflowV2IntentMessage fulfillmentMessage, String output) {
        List<GoogleCloudDialogflowV2IntentMessage> actualMessages = request.getQueryResult().getFulfillmentMessages();

        // Borra "suggestions antiguas" para añadir las del return context para que se vuelvan a mostrar las suggestion chips.
        for (GoogleCloudDialogflowV2IntentMessage message : actualMessages) {
            if (message.getSuggestions() != null) {
                actualMessages.remove(message);
            }
        }
        actualMessages.add(fulfillmentMessage);

        GoogleCloudDialogflowV2IntentMessage messageOutput = new GoogleCloudDialogflowV2IntentMessage();
        GoogleCloudDialogflowV2IntentMessageText text = new GoogleCloudDialogflowV2IntentMessageText();
        text.setText(new ArrayList<>(Arrays.asList(output)));
        messageOutput.setText(text);
        actualMessages.add(messageOutput);
        return actualMessages;
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

    public void clearUndoneQuestionnaries() {
        activeQuestionnaries.clear();
        logger.info("All open questionnaries were removed.");
    }
}
