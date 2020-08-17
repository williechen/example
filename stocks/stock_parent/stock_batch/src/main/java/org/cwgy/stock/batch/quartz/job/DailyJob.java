package org.cwgy.stock.batch.quartz.job;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DailyJob extends QuartzJobBean {
	Logger log = LogManager.getLogger(getClass());

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {

			JobDataMap jobDataMap = context.getMergedJobDataMap();

			System.out.println("jobName=" + jobDataMap.get("batchNames"));
			System.out.println("jobLauncher=" + jobDataMap.get("jobLauncher"));
			System.out.println("jobLocator=" + jobDataMap.get("jobLocator"));

			List<String> batchNames = (List<String>) jobDataMap.get("batchNames");
			JobLauncher jobLauncher = (JobLauncher) jobDataMap.get("jobLauncher");
			JobLocator jobLocator = (JobLocator) jobDataMap.get("jobLocator");

			if (batchNames != null && batchNames.size() > 0) {
				for (String batchName : batchNames) {
					
					Job job = jobLocator.getJob(batchName);
					JobParameters params = new JobParametersBuilder()
							.addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();

					jobLauncher.run(job, params);

				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
