package es.uji.giant.DialogFlowTests.controller;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2Context;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2EventInput;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2.model.GoogleCloudDialogflowV2WebhookResponse;
import es.uji.giant.DialogFlowTests.model.Test;
import es.uji.giant.DialogFlowTests.repository.TestDao;
import es.uji.giant.DialogFlowTests.utils.Constants;
import es.uji.giant.DialogFlowTests.utils.Input;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DialogflowController extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, Test> activeTests;
    private TestDao testDao;

    @Autowired
    public DialogflowController(TestDao testDao) {
        this.testDao = testDao;
        activeTests = new HashMap<>();
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

            // Check session.
            logger.info("Active Intent: " + activeIntent);
            String session = request.getSession();
            logger.info("Active Session: " + session);

            // Check intent
            switch (activeIntent) {
                case Constants.SEX_INTENT:  parameter = String.valueOf(parameters.get("sex"));
                                            if (!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidSex(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 1);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 1);
                                                    output = Constants.NOT_VALID_SEX_ANSWER;
                                                }

                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.AGE_INTENT:  parameter = String.valueOf(parameters.get("age"));
                                            if (!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidAge(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 2);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 2);
                                                    output = Constants.NOT_VALID_AGE_ANSWER;
                                                }

                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.ALONE_INTENT:parameter = String.valueOf(parameters.get("alone"));
                                            if (!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidAlone(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 3);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 3);
                                                    output = Constants.NOT_VALID_ALONE_ANSWER;
                                                }

                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.JONG1_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidJong(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 4);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 4);
                                                    output = Constants.NOT_VALID_JONG_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.JONG2_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidJong(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 5);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 5);
                                                    output = Constants.NOT_VALID_JONG_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.JONG3_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidJong(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 6);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 6);
                                                    output = Constants.NOT_VALID_JONG_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.JONG4_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidJong(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 7);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 7);
                                                    output = Constants.NOT_VALID_JONG_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.JONG5_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidJong(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 8);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 8);
                                                    output = Constants.NOT_VALID_JONG_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.JONG6_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidJong(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 9);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 9);
                                                    output = Constants.NOT_VALID_JONG_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.UCLA1_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidUCLA(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 10);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 10);
                                                    output = Constants.NOT_VALID_UCLA_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.UCLA2_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidUCLA(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 11);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 11);
                                                    output = Constants.NOT_VALID_UCLA_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.UCLA3_INTENT:parameter = String.valueOf(parameters.get("val"));
                                            if(!Input.userWantsToCancel(parameter)) {
                                                thereIsContext = true;
                                                if (Input.isValidUCLA(parameter)) {
                                                    outputContext = fillInformation(parameter, session, 12);
                                                    output = request.getQueryResult().getFulfillmentText();
                                                } else {
                                                    outputContext = returnContext(session, 12);
                                                    output = Constants.NOT_VALID_UCLA_ANSWER;
                                                }
                                            } else {
                                                thereIsEvent = true;
                                                output = "Operación cancelada... ¿En que te puedo ayudar?";
                                                eventInput = new GoogleCloudDialogflowV2EventInput();
                                                eventInput.setName("Welcome");
                                                logger.info("El usuario ha cancelado la conversación");
                                            } break;

                case Constants.USER_COMMENT_INTENT:
                                            parameter = request.getQueryResult().getQueryText();
                                            outputContext = fillInformation(parameter, session, 13);
                                            output = "Muchas gracias, hemos terminado!";
                                            break;
            }

            logger.info("\n\nActive Test");
            for (Map.Entry<String, Test> entry : activeTests.entrySet()) {
                logger.info(entry.getKey() + ": " + entry.getValue());
            }

            // Response
            response.setFulfillmentText(output);
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

    // typeOfInformation -> (1, sexo), (2, edad), (3, vive solo)
    private GoogleCloudDialogflowV2Context fillInformation (String parameter, String session, int typeOfInformation) {
        String sessionId = session.split("/")[4];
        GoogleCloudDialogflowV2Context outputContext = new GoogleCloudDialogflowV2Context();
        int lifespan = 1;
        outputContext.setLifespanCount(lifespan);

        switch (typeOfInformation) {
            case 1: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).setSex(parameter);
                    } else {
                        Test test = new Test();
                        test.setSex(parameter.toLowerCase());
                        activeTests.put(sessionId, test);
                    }

                    outputContext.setName(session + "/contexts/age");
                    break;

            case 2: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).setAge(Integer.valueOf(parameter));
                    }

                    outputContext.setName(session + "/contexts/solo");
                    break;

            case 3: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).setLiveAlone(parameter.toLowerCase().equals("si") || parameter.toLowerCase().equals("sí"));
                    }

                    outputContext.setName(session + "/contexts/val1");
                    break;

            case 4: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                    }

                    outputContext.setName(session + "/contexts/val2");
                    break;

            case 5: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                    }

                    outputContext.setName(session + "/contexts/val3");
                    break;

            case 6: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                    }

                    outputContext.setName(session + "/contexts/val4");
                    break;

            case 7: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                    }

                    outputContext.setName(session + "/contexts/val5");
                    break;

            case 8: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                    }

                    outputContext.setName(session + "/contexts/val6");
                    break;

            case 9: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                    }

                    outputContext.setName(session + "/contexts/val7");
                    break;

            case 10: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                    }

                    outputContext.setName(session + "/contexts/val8");
                    break;

            case 11: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                    }

                    outputContext.setName(session + "/contexts/val9");
                    break;

            case 12: if(activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).addAnswer(parameter.toLowerCase());
                        activeTests.get(sessionId).calculateJongScore();
                        activeTests.get(sessionId).calculateUCLAScore();
                        activeTests.get(sessionId).createDiagnosis();
                    }

                    outputContext.setName(session + "/contexts/user_comment");
                    break;

            case 13: if (activeTests.containsKey(sessionId)) {
                        activeTests.get(sessionId).setUserComments(parameter);
                        activeTests.get(sessionId).setTimestamp(System.currentTimeMillis());

                        testDao.insertTest(sessionId, activeTests.get(sessionId));
                        activeTests.remove(sessionId);

                        }
                    break;
        }

        return outputContext;
    }

    private GoogleCloudDialogflowV2Context returnContext (String session, int typeOfInformation) {
        GoogleCloudDialogflowV2Context outputContext = new GoogleCloudDialogflowV2Context();
        int lifespan = 1;
        outputContext.setLifespanCount(lifespan);

        switch (typeOfInformation) {
            case 1: outputContext.setName(session + "/contexts/sex");
                    break;

            case 2: outputContext.setName(session + "/contexts/age");
                    break;

            case 3: outputContext.setName(session + "/contexts/solo");
                    break;

            case 4: outputContext.setName(session + "/contexts/val1");
                    break;

            case 5: outputContext.setName(session + "/contexts/val2");
                    break;

            case 6: outputContext.setName(session + "/contexts/val3");
                    break;

            case 7: outputContext.setName(session + "/contexts/val4");
                    break;

            case 8: outputContext.setName(session + "/contexts/val5");
                    break;

            case 9: outputContext.setName(session + "/contexts/val6");
                    break;

            case 10: outputContext.setName(session + "/contexts/val7");
                    break;

            case 11: outputContext.setName(session + "/contexts/val8");
                    break;

            case 12: outputContext.setName(session + "/contexts/val9");
                    break;

        }

        return outputContext;
    }
}
