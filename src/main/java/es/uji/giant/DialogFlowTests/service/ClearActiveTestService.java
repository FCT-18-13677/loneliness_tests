package es.uji.giant.DialogFlowTests.service;

import es.uji.giant.DialogFlowTests.listener.ClearMapListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearActiveTestService {
    private ClearMapListener listener;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ClearActiveTestService () {
        listener = null;
    }

    //@Scheduled(cron = "0 1 1 * * ?")                // Alarma cada día a la 01:01 am
    @Scheduled(cron = "0 0/30 * * * ?")                // Alarma cada 30 minutos
    public void scheduleTaskWithCronExpression() {
        logger.info("\nSalta la alarma ClearTestService\n");
        listener.clearMap();
    }

    public void setListener(ClearMapListener listener) {
        this.listener = listener;
    }
}
