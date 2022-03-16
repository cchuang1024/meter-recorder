package edu.nccu.cs.recorder.runner;

import java.util.concurrent.ExecutionException;

import edu.nccu.cs.recorder.domain.MeterData;
import edu.nccu.cs.recorder.job.MeterReadingJob;
import edu.nccu.cs.recorder.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainRunner {

    @Autowired
    private ApplicationContext context;
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Scheduled(fixedRate = 60 * 1000L)
    public void fetchMeterData() {
        MeterReadingJob job = context.getBean(MeterReadingJob.class);
        try {
            MeterData meterData = taskExecutor.submit(job)
                                              .get();

            log.warn("meter data: {}", meterData);
        } catch (InterruptedException | ExecutionException ex) {
            log.error(ExceptionUtils.getStackTrace(ex));
        }
    }
}
