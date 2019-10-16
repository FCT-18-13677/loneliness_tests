package es.uji.giant.DialogFlowTests.service;

import es.uji.giant.DialogFlowTests.listener.ClearMapListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearActiveTestService {
    private ClearMapListener listener;

    public ClearActiveTestService () {
        listener = null;
    }

    @Scheduled(cron = "0 1 1 * * ?")                // Alarma cada día a la 01:01 am
    public void scheduleTaskWithCronExpression() {
        listener.clearMap();
    }

    public void setListener(ClearMapListener listener) {
        this.listener = listener;
    }
}
