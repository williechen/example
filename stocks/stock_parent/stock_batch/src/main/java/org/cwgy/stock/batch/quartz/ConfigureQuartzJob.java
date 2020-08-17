package org.cwgy.stock.batch.quartz;

import org.cwgy.stock.batch.quartz.job.DailyJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureQuartzJob {
	@Bean
	public JobDetail jobADetails() {
		return JobBuilder.newJob(DailyJob.class).withIdentity("sampleJobA").storeDurably().build();
	}

	@Bean
	public Trigger jobATrigger(JobDetail jobADetails) {
		// 秒 分 时 天 月 周 年
		return TriggerBuilder.newTrigger().forJob(jobADetails).withIdentity("TriggerA")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * ? * * *")).build();
	}

}