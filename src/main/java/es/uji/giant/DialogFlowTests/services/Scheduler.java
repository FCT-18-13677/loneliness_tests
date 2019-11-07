package es.uji.giant.DialogFlowTests.services;

import es.uji.giant.DialogFlowTests.controller.DialogflowController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DialogflowController controller;

    //@Scheduled(cron = "0 * * * * ?")              // Alarma cada minuto, para test
    @Scheduled(cron = "0 1 1 * * ?")                // Alarma diaría a la 1:01
    public void scheduleTaskWithCronExpression() {
        logger.info("---------------------------------------------------> Alarm ring!!");
        controller.clearUndoneQuestionnaries();
    }
}
