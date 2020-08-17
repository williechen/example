package org.cwgy.stock.batch.config.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cwgy.stock.batch.quartz.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SchedulerStartUpHandler implements ApplicationRunner {
	Logger log = LogManager.getLogger(getClass());

    @Autowired
    private SchedulerService schedulerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Schedule all new scheduler jobs at app startup - starting");
        try {
            schedulerService.startAllSchedulers();
            log.info("Schedule all new scheduler jobs at app startup - complete");
        } catch (Exception ex) {
            log.error("Schedule all new scheduler jobs at app startup - error", ex);
        }
    }
}
