package org.cwgy.stock.batch.quartz.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class DailyTask implements Tasklet {
	Logger log = LogManager.getLogger(getClass());

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("MyTaskOne start..");

        System.out.println("MyTaskOne done..");
        return RepeatStatus.FINISHED;
	}

}
