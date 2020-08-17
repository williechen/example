package org.cwgy.stock.batch.quartz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cwgy.stock.batch.config.quartz.JobScheduleCreator;
import org.cwgy.stock.batch.enitiy.SchedulerJobInfo;
import org.cwgy.stock.batch.quartz.service.SchedulerService;
import org.quartz.CronExpression;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional("secondTransactionManager")
@Service
public class SchedulerServiceImpl implements SchedulerService {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private JobScheduleCreator scheduleCreator;

	/**
	 * 初始已建立排程工作
	 */
	@Override
	public void startAllSchedulers() {
		List<SchedulerJobInfo> jobInfoList = new ArrayList<SchedulerJobInfo>();

		SchedulerJobInfo jobInfo1 = new SchedulerJobInfo();
		jobInfo1.setJobClass("org.cwgy.stock.batch.quartz.job.DailyJob");
		jobInfo1.setJobName("DailyJob");
		jobInfo1.setJobGroup("daily");
		// {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
		jobInfo1.setCronExpression("0 */1 * ? * * *");
		jobInfo1.setCronJob(true);
		jobInfo1.setRepeatTime(60L * 5);
		
		List<String> batchNames = new ArrayList<String>();
		batchNames.add("demoJobOne");

		jobInfo1.setBatchNames(batchNames);
		
		jobInfoList.add(jobInfo1);
		
		if (jobInfoList != null) {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			for (SchedulerJobInfo jobInfo : jobInfoList) {
				try {
					JobDetail jobDetail = JobBuilder
							.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
							.withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup())
							.build();
					
					if (!scheduler.checkExists(jobDetail.getKey())) {
						Trigger trigger;
						jobDetail = scheduleCreator.createJob(
								(Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()), false, context,
								jobInfo.getJobName(), jobInfo.getJobGroup(), jobInfo.getBatchNames());

						if (jobInfo.getCronJob() && CronExpression.isValidExpression(jobInfo.getCronExpression())) {
							trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
									jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
						} else {
							trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
									jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
						}

						scheduler.scheduleJob(jobDetail, trigger);

					} else {
						this.updateScheduleJob(jobInfo1);
					}
				} catch (ClassNotFoundException e) {
					log.error("Class Not Found - {}", jobInfo.getJobClass(), e);
				} catch (SchedulerException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 建立新工作
	 */
	@Override
	public void scheduleNewJob(SchedulerJobInfo jobInfo) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			JobDetail jobDetail = JobBuilder
					.newJob((Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()))
					.withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
			if (!scheduler.checkExists(jobDetail.getKey())) {

				jobDetail = scheduleCreator.createJob(
						(Class<? extends QuartzJobBean>) Class.forName(jobInfo.getJobClass()), false, context,
						jobInfo.getJobName(), jobInfo.getJobGroup(), jobInfo.getBatchNames());

				Trigger trigger;
				if (jobInfo.getCronJob()) {
					trigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
							jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				} else {
					trigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(),
							jobInfo.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				}

				scheduler.scheduleJob(jobDetail, trigger);

			} else {
				log.error("scheduleNewJobRequest.jobAlreadyExist");
			}
		} catch (ClassNotFoundException e) {
			log.error("Class Not Found - {}", jobInfo.getJobClass(), e);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 更新舊工作
	 */
	@Override
	public void updateScheduleJob(SchedulerJobInfo jobInfo) {
		Trigger newTrigger;
		if (jobInfo.getCronJob()) {
			newTrigger = scheduleCreator.createCronTrigger(jobInfo.getJobName(), new Date(),
					jobInfo.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		} else {
			newTrigger = scheduleCreator.createSimpleTrigger(jobInfo.getJobName(), new Date(), jobInfo.getRepeatTime(),
					SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		}

		try {
			schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobInfo.getJobName()), newTrigger);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * 刪除非排程工作
	 */
	@Override
	public boolean unScheduleJob(String jobName) {
		try {
			return schedulerFactoryBean.getScheduler().unscheduleJob(new TriggerKey(jobName));
		} catch (SchedulerException e) {
			log.error("Failed to un-schedule job - {}", jobName, e);
			return false;
		}
	}

	/**
	 * 刪除工作
	 */
	@Override
	public boolean deleteJob(SchedulerJobInfo jobInfo) {
		try {
			return schedulerFactoryBean.getScheduler()
					.deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
		} catch (SchedulerException e) {
			log.error("Failed to delete job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}

	/**
	 * 暫停工作
	 */
	@Override
	public boolean pauseJob(SchedulerJobInfo jobInfo) {
		try {
			schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
			return true;
		} catch (SchedulerException e) {
			log.error("Failed to pause job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}

	/**
	 * 恢復工作
	 */
	@Override
	public boolean resumeJob(SchedulerJobInfo jobInfo) {
		try {
			schedulerFactoryBean.getScheduler().resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
			return true;
		} catch (SchedulerException e) {
			log.error("Failed to resume job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}

	/**
	 * 即時開始工作
	 */
	@Override
	public boolean startJobNow(SchedulerJobInfo jobInfo) {
		try {
			schedulerFactoryBean.getScheduler().triggerJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
			return true;
		} catch (SchedulerException e) {
			log.error("Failed to start new job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}
}
